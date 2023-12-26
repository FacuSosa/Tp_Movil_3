package ar.edu.unlam.mobile.scaffold.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.IOrientationDataManager
import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.SensorData
import ar.edu.unlam.mobile.scaffold.data.repository.herorepository.IHeroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(
    private val repo: IHeroRepository,
    private val orientationDataManager: IOrientationDataManager
) : ViewModel() {

    private val _sensorData = MutableStateFlow(SensorData())
    val sensorData = _sensorData.asStateFlow()

    private val _cachingProgress = MutableStateFlow(0.00f)
    val cachingProgress = _cachingProgress.asStateFlow()

    init {
        viewModelScope.launch {
            orientationDataManager.getSensorData().collect {
                _sensorData.value = it
            }
        }
        viewModelScope.launch {
            preloadHeroes()
        }
    }

    private suspend fun preloadHeroes() {
        withContext(Dispatchers.IO) {
            val cachingProgressFlow = repo.preloadHeroCache()
            cachingProgressFlow
                .collect { progress ->
                    _cachingProgress.value = progress
                }
        }
    }

    fun cancelSensorDataFlow() {
        orientationDataManager.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        orientationDataManager.cancel()
    }
}
