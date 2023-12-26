package ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import kotlinx.coroutines.flow.Flow

interface IOrientationDataManager : SensorEventListener {

    fun cancel()

    override fun onSensorChanged(event: SensorEvent?)
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int)
    fun getSensorData(): Flow<SensorData>
}

data class SensorData(
    val roll: Float = 0f, // Roll (rotation around the y-axis)
    val pitch: Float = 0f // Pitch (rotation around the x-axis)
)
