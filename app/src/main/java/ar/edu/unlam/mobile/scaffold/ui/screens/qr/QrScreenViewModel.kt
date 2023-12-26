package ar.edu.unlam.mobile.scaffold.ui.screens.qr

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffold.data.repository.herorepository.IHeroRepository
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class QrScreenViewModel @Inject constructor(
    private val repo: IHeroRepository
) : ViewModel() {

    private val _hero = MutableStateFlow(HeroModel())
    val hero = _hero.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    fun getHero(id: Int) {
        viewModelScope.launch {
            _hero.value = withContext(Dispatchers.IO) {
                repo.getHero(id)
            }
            _isLoading.value = false
        }
    }
}