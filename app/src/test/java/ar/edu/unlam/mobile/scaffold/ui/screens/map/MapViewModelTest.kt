package ar.edu.unlam.mobile.scaffold.ui.screens.map

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ar.edu.unlam.mobile.scaffold.MainDispatcherRule
import ar.edu.unlam.mobile.scaffold.domain.map.GetLocationUseCase
import com.google.android.gms.maps.model.LatLng
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MapViewModelTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    lateinit var viewModel: MapViewModel

    @MockK
    lateinit var getLocationUseCase: GetLocationUseCase

    @Before
    fun setUp() {
        every { getLocationUseCase.invoke() } returns flow {
            emit(LatLng(0.0, 0.0))
        }
        viewModel = MapViewModel(getLocationUseCase)
    }

    @Test
    fun cuandoElPermisoDeLalocalizacionEsteOrtogadoVerificarQueLaLocalizacionEsperadoSeaLaCorrecta() = runTest {
        val permissionEvent = PermissionEvent.Granted
        val localizacionEsperada = LatLng(0.0, 0.0)

        viewModel.handle(permissionEvent)
        while (viewModel.viewState.value == ViewState.Loading) {
            delay(500)
        }
        val localizacion = (viewModel.viewState.value as ViewState.Success).location!!

        assertThat(localizacion).isEqualTo(localizacionEsperada)
    }

    @Test
    fun cuandoElPermisoDeLaLocacionOtorgadoEsRechazado() = runTest {
        val permissionEvent = PermissionEvent.Revoked
        val viewStateEsperado = ViewState.RevokedPermissions

        viewModel.handle(permissionEvent)
        while (viewModel.viewState.value == ViewState.Loading) {
            delay(500)
        }
        val viewState = viewModel.viewState.value

        assertThat(viewState).isEqualTo(viewStateEsperado)
    }
}
