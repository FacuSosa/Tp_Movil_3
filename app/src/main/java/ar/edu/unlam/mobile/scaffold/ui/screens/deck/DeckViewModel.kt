package ar.edu.unlam.mobile.scaffold.ui.screens.deck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffold.data.repository.deckrepository.IDeckRepository
import ar.edu.unlam.mobile.scaffold.data.repository.herorepository.IHeroRepository
import ar.edu.unlam.mobile.scaffold.domain.model.DeckModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeckViewModel @Inject constructor(
    private val deckRepository: IDeckRepository,
    private val heroRepository: IHeroRepository
) : ViewModel() {
    private val _listDeck = MutableStateFlow<List<DeckModel>>(emptyList())
    val listDeck = _listDeck.asStateFlow()

    private val _randomDeck = MutableStateFlow(DeckModel(id = null))
    val randomDeck = _randomDeck.asStateFlow()

    private val _pantallaActual = MutableStateFlow(DeckUI.LISTA_DE_MAZOS)
    val pantallaActual = _pantallaActual.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    fun generarMazoRandom() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            _randomDeck.value = heroRepository.getRandomDeck()
            _isLoading.value = false
        }
    }

    fun guardarMazoRandom() {
        viewModelScope.launch(Dispatchers.IO) {
            deckRepository.insertDeck(_randomDeck.value)
        }
    }

    fun obtenerTodosLosMazos() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            _listDeck.value = deckRepository.getDeckList()
            _isLoading.value = false
        }
    }
    fun irAListaDeMazos() {
        obtenerTodosLosMazos()
        _pantallaActual.value = DeckUI.LISTA_DE_MAZOS
    }
    fun irAGenerarMazos() {
        generarMazoRandom()
        _pantallaActual.value = DeckUI.GENERAR_MAZOS
    }
    init {
        obtenerTodosLosMazos()
    }
}
