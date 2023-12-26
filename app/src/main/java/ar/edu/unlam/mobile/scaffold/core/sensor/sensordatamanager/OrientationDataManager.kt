package ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.util.Log
import ar.edu.unlam.mobile.scaffold.core.sensor.OrientationDataManagerException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

/*
class OrientationDataManager(context: Context) : SensorEventListener {

    private val sensorManager by lazy {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
 */
class OrientationDataManager @Inject constructor(private val sensorManager: SensorManager) :
    IOrientationDataManager {

    private var gravity: FloatArray? = null
    private var geomagnetic: FloatArray? = null

    private val data: Channel<SensorData> = Channel(Channel.UNLIMITED)

    override fun getSensorData() = flow {
        init()
        data.receiveAsFlow().collect {
            emit(it)
        }
    }

    /*
        TYPE_ACCELEROMETER
        Type: Hardware
        Computes the acceleration in m/s2 applied on all three axes (x, y and z),
        including the force of gravity.

        TYPE_GRAVITY es lo mismo que type_accelerometer pero nomas mide la gravedad
        Type: Software or Hardware
        Computes the gravitational force in m/s2 applied on all three axes (x, y and z).

        TYPE_MAGNETIC_FIELD
        Type: Hardware
        Computes the geomagnetic field for all three axes in tesla (μT).

        Existe el Sensor.TYPE_ACCELEROMETER, creo que no lo usa porque TYPE_GRAVITY puede ser
            emulado por software usando otros sensores.
     */
    private fun init() {
        Log.d("OrientationDataManager", "init")
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)

        val magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        val isAccelerometerNotSuccessful = !sensorManager.registerListener(
            this,
            accelerometer,
            SensorManager.SENSOR_DELAY_UI
        )
        val isMagnetometerNotSuccessful = !sensorManager.registerListener(
            this,
            magnetometer,
            SensorManager.SENSOR_DELAY_UI
        )
        if (isAccelerometerNotSuccessful || isMagnetometerNotSuccessful) {
            Log.d(
                "OrientationDataManager",
                "accelerometer: ${!isAccelerometerNotSuccessful}" +
                    " magnetometer: ${!isMagnetometerNotSuccessful}"
            )
            cancel()
            throw OrientationDataManagerException(
                message = "El sensor acelerómetro o el magnetómetro no está disponible en este dispositivo."
            )
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_GRAVITY || event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            gravity = event.values
        }

        if (event?.sensor?.type == Sensor.TYPE_MAGNETIC_FIELD) {
            geomagnetic = event.values
        }

        if (gravity != null && geomagnetic != null) {
            val rotationMatrix = FloatArray(9)
            val i = FloatArray(9)

            if (SensorManager.getRotationMatrix(rotationMatrix, i, gravity, geomagnetic)) {
                val orientation = FloatArray(3)
                SensorManager.getOrientation(rotationMatrix, orientation)

                if (!orientation[1].isNaN() && !orientation[2].isNaN()) {
                    data.trySend(
                        SensorData(
                            roll = orientation[2], // Roll (rotation around the y-axis)
                            pitch = orientation[1] // Pitch (rotation around the x-axis)
                        )
                    )
                }
            }
        }
    }

    override fun cancel() {
        Log.d("OrientationDataManager", "cancel")
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // do nothing
    }
}
