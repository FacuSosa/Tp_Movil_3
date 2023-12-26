package ar.edu.unlam.mobile.scaffold.ui.screens.herodetail

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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HeroDetailViewModelImpTest {

    /*
        JUnit 4 exposes a rule-based API to allow for some automation following the test lifecycle.
        MockK includes a rule which uses this to set up and tear down your mocks without needing to
        manually call MockKAnnotations.init(this).
     */
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
    lateinit var repo: IHeroRepository

    @RelaxedMockK
    lateinit var orientationDataManager: IOrientationDataManager

    lateinit var viewModel: HeroDetailViewModelImp

    @Before
    fun setUp() {
        coEvery { repo.getHero(1) } returns HeroModel(id = 1, name = "Mr. Test")
        every { orientationDataManager.getSensorData() } returns flow { emit(SensorData(0f, 0f)) }
        viewModel = HeroDetailViewModelImp(repo, orientationDataManager)
    }

    // runTest es una corutina para testing, lo que permite usar funciones suspend
    // coEvery es lo mismo que un Every pero permite dar comportamiento a funciones suspend
    @Test
    fun whenPassingAnId_returnTheCorrectHero() = runTest {
        val expectedHero = HeroModel(id = 1, name = "Mr. Test")

        while (viewModel.isLoading.value) {
            delay(500)
        }
        viewModel.getHero(1)
        while (viewModel.isLoading.value) {
            delay(500)
        }
        val hero = viewModel.hero.value

        assertThat(hero).isEqualTo(expectedHero)
    }

    /*
    @Test
    fun getSensorData() {
    }

    @Test
    fun getHero() {
    }

    @Test
    fun isLoading() {
    }

    @Test
    fun testGetHero() {
    }

    @Test
    fun cancelSensorDataFlow() {
    }

    @Test
    fun onCleared() {
    }*/
}
