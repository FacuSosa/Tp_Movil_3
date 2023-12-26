package ar.edu.unlam.mobile.scaffold.ui.screens.usuario

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ar.edu.unlam.mobile.scaffold.MainDispatcherRule
import ar.edu.unlam.mobile.scaffold.data.database.guest.GuestRepository
import ar.edu.unlam.mobile.scaffold.domain.usuario.Guest
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UsuarioViewModelTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @RelaxedMockK
    lateinit var repo: GuestRepository
    lateinit var viewModel: UsuarioViewModel

    @Before
    fun setUp() {
        coEvery { repo.usuarioExiste() } returns Guest(1, "Pepito")
        viewModel = UsuarioViewModel(repo)
    }

    @Test
    fun crearUsuario() = runTest {
        val nombre = "Pepito"
        val usuarioEsperado = Guest(1, "Pepito")

        viewModel.crearUsuario(nombre)
        viewModel.obtenerUsuario()
        val usuario = viewModel.usuario.value

        assertThat(usuario).isEqualTo(usuarioEsperado)
    }
}
