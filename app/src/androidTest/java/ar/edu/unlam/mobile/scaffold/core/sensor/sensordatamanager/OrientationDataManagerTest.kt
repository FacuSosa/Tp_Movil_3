package ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager

import android.content.Context
import android.hardware.SensorManager
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class OrientationDataManagerTest {

    /*
        Esta regla es para que se ejecute cada línea de código de forma secuencial.
        Nos sirve en casos en donde se necesite el uso de livedata, flow, suspend functions, etc.
     */
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var orientationDataManager: OrientationDataManager

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        val sensorManager by lazy {
            context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        }
        orientationDataManager = OrientationDataManager(sensorManager)
    }

    @After
    fun tearDown() {
        orientationDataManager.cancel()
    }

    @Test
    fun whenCollectingFlowFromGetSensorDataItMustEmitFiniteNumbers() = runTest {
        orientationDataManager.getSensorData().test {
            val sensorData = awaitItem()
            assertThat(sensorData.roll).isNotNaN()
            assertThat(sensorData.pitch).isNotNaN()
            assertThat(sensorData.roll).isFinite()
            assertThat(sensorData.pitch).isFinite()
            cancel()
        }
    }
}
