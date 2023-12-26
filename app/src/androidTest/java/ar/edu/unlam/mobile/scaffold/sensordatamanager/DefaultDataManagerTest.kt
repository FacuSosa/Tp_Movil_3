package ar.edu.unlam.mobile.scaffold.sensordatamanager

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.DefaultDataManager
import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.SensorData
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class DefaultDataManagerTest {

    /*
        Esta regla es para que se ejecute cada línea de código de forma secuencial.
        Nos sirve en casos en donde se necesite el uso de livedata, flow, suspend functions, etc.
     */
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    /*
        @relaxedmockk
        se pueden mockear manualmente los métodos que necesitamos y el resto se genera
        automáticamente.

        @mockk
        debemos generar manualmente una respuesta para todos los métodos.
     */

    lateinit var dataManager: DefaultDataManager

    @Before
    fun setUp() {
        dataManager = DefaultDataManager()
    }

    @Test
    fun whenCollectingFlowFromGetSensorDataItMustEmitSensorDataWithItsValuesAt0f() =
        runTest {
            val expected = SensorData(0f, 0f)
            dataManager.getSensorData().test {
                val sensorData = awaitItem()
                assertThat(sensorData.roll).isEqualTo(expected.roll)
                assertThat(sensorData.pitch).isEqualTo(expected.pitch)
                awaitComplete()
            }
        }
}
