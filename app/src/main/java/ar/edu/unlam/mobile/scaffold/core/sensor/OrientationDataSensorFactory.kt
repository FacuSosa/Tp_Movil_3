package ar.edu.unlam.mobile.scaffold.core.sensor

import android.hardware.Sensor
import android.hardware.SensorManager
import android.util.Log
import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.DefaultDataManager
import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.GameRotationVectorSensorManager
import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.IOrientationDataManager
import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.OrientationDataManager
import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.RotationVectorSensorManager
import javax.inject.Inject

class OrientationDataSensorFactory @Inject constructor(private val sensorManager: SensorManager) {

    private var availableSensor: AvailableSensor

    init {
        val sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL).map { it.type }
        availableSensor = if (sensorList.contains(Sensor.TYPE_GAME_ROTATION_VECTOR)) {
            AvailableSensor.GAME_VECTOR
        } else {
            if (sensorList.contains(Sensor.TYPE_ROTATION_VECTOR)) {
                AvailableSensor.ROTATION_VECTOR
            } else {
                val containsGravity = sensorList.contains(Sensor.TYPE_GRAVITY)
                val containsMagnetic = sensorList.contains(Sensor.TYPE_MAGNETIC_FIELD)
                if (containsGravity && containsMagnetic) {
                    AvailableSensor.GRAVITY_AND_MAGNETIC_FIELD
                } else {
                    AvailableSensor.NONE
                }
            }
        }
    }

    fun getOrientationDataManager(): IOrientationDataManager {
        return when (availableSensor) {
            AvailableSensor.GAME_VECTOR -> {
                Log.d("OrientationDataSensorFactory", "Se ha instanciado GameRotationVectorSensorManager")
                GameRotationVectorSensorManager(sensorManager)
            }
            AvailableSensor.ROTATION_VECTOR -> {
                Log.d("OrientationDataSensorFactory", "Se ha instanciado RotationVectorSensorManager")
                RotationVectorSensorManager(sensorManager)
            }
            AvailableSensor.GRAVITY_AND_MAGNETIC_FIELD -> {
                Log.d("OrientationDataSensorFactory", "Se ha instanciado OrientationDataManager")
                OrientationDataManager(sensorManager)
            }
            AvailableSensor.NONE -> {
                Log.d("OrientationDataSensorFactory", "Se ha instanciado DefaultDataManager")
                DefaultDataManager()
            }
        }
    }
}
