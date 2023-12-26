package ar.edu.unlam.mobile.scaffold.ui.screens.collection

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ar.edu.unlam.mobile.scaffold.MainDispatcherRule
import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.IOrientationDataManager
import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.SensorData
import ar.edu.unlam.mobile.scaffold.data.repository.herorepository.IHeroRepository
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CollectionViewModelImpTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    /*
        Esta regla es para que se ejecute cada línea de código de forma secuencial.
        Nos sirve en casos en donde se necesite el uso de livedata, flow, suspend functions, etc.
     */
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule() // leer la descripción de la clase

    @RelaxedMockK
    lateinit var sensorManager: IOrientationDataManager

    @RelaxedMockK
    lateinit var heroRepository: IHeroRepository

    private lateinit var viewModel: CollectionViewModelImp

    private val heroList = listOf(
        HeroModel(id = 1, name = "Test 1"),
        HeroModel(id = 2, name = "Test 2"),
        HeroModel(id = 3, name = "Test 3"),
        HeroModel(id = 4, name = "Test 4"),
        HeroModel(id = 5, name = "Test 5"),
        HeroModel(id = 6, name = "Test 6"),
        HeroModel(id = 7, name = "Test 7"),
        HeroModel(id = 8, name = "Test 8"),
        HeroModel(id = 9, name = "Test 9")
    )

    @Before
    fun setUp() {
        every { sensorManager.getSensorData() } returns flow { emit(SensorData()) }
        coEvery { heroRepository.getAllHero() } returns heroList
        viewModel = CollectionViewModelImp(repo = heroRepository, orientationDataManager = sensorManager)
    }

    @Test
    fun `after viewModel finishes loading, verify if default value of sensorData is 0f, 0f`() = runTest {
        val expectedSensorData = SensorData(0f, 0f)
        while (viewModel.isLoading.value) {
            delay(500)
        }
        val sensorData = viewModel.sensorData.value
        assertThat(sensorData).isEqualTo(expectedSensorData)
    }

    @Test
    fun `after viewModel finishes loading, verify if its heroList is the same provided by the repository`() = runTest {
        while (viewModel.isLoading.value) {
            delay(500)
        }
        val viewModelHeroList = viewModel.heroList.value

        assertThat(viewModelHeroList).containsExactlyElementsIn(heroList).inOrder()
    }

    @Test
    fun `after viewModel finishes loading, verify cancelSensorDataFlow calls sensorData cancel once`() = runTest {
        while (viewModel.isLoading.value) {
            delay(500)
        }
        viewModel.cancelSensorDataFlow()
        verify(exactly = 1) { sensorManager.cancel() }
    }
}
