package ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.util.Log
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultDataManager @Inject constructor() : IOrientationDataManager {

    override fun getSensorData() = flow {
        emit(
            SensorData()
        )
    }

    init {
        Log.d(
            "OrientationDataManager",
            "Default orientation data manager initiated. " +
                "The app doesn't find the sensors needed to work properly."
        )
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d("OrientationDataManager", "onAccuracyChanged does nothing")
    }

    override fun onSensorChanged(event: SensorEvent?) {
        Log.d("OrientationDataManager", "onSensorChanged does nothing")
    }
    override fun cancel() {
        Log.d("OrientationDataManager", "cancel does nothing")
    }
}
