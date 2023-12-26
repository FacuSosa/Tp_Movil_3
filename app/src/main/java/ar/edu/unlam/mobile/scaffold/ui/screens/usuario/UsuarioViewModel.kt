package ar.edu.unlam.mobile.scaffold.ui.screens.usuario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffold.data.database.guest.GuestRepository
import ar.edu.unlam.mobile.scaffold.domain.usuario.Guest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class UsuarioViewModel @Inject constructor(
    private val repository: GuestRepository
) : ViewModel() {

    private val _existeGuest = MutableStateFlow(false)
    val existeGuest: StateFlow<Boolean> = _existeGuest

    private val _usuario = MutableStateFlow(Guest(1, ""))
    val usuario: StateFlow<Guest> = _usuario

    fun crearUsuario(name: String) {
        val guest = Guest(1, name)
        viewModelScope.launch {
            repository.addGuestInDatabase(guest)
        }
    }
    fun obtenerUsuario() {
        viewModelScope.launch {
            val usuario = withContext(Dispatchers.IO) {
                repository.usuarioExiste()
            }
            if (usuario == null) {
                _existeGuest.value = false
            } else {
                _usuario.value = usuario!!
                _existeGuest.value = true
            }
        }
    }
    init {
        obtenerUsuario()
    }
}
