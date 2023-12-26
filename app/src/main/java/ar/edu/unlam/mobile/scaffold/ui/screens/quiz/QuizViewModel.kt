package ar.edu.unlam.mobile.scaffold.ui.screens.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.IOrientationDataManager
import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.SensorData
import ar.edu.unlam.mobile.scaffold.data.repository.quizrepository.IQuizGameRepository
import ar.edu.unlam.mobile.scaffold.domain.quiz.QuizGame
import ar.edu.unlam.mobile.scaffold.domain.quiz.QuizOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// quitadas las interfaces de los viewmodels ya que, hilt solamente soporta viewModels que solamente
// hereden de ViewModel y que no implementen interfaces.
@HiltViewModel
class QuizViewModel @Inject constructor(
    private val repo: IQuizGameRepository,
    private val orientationDataManager: IOrientationDataManager
) : ViewModel() {

    private lateinit var game: QuizGame

    private val _sensorData = MutableStateFlow(SensorData())
    val sensorData = _sensorData.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _heroPortraitUrl = MutableStateFlow("https://loremflickr.com/400/400/cat?lock=1")
    val heroPortraitUrl = _heroPortraitUrl.asStateFlow()

    private val _option1 = MutableStateFlow("option 1")
    val option1 = _option1.asStateFlow()

    private val _option2 = MutableStateFlow("option 2")
    val option2 = _option2.asStateFlow()

    private val _option3 = MutableStateFlow("option 3")
    val option3 = _option3.asStateFlow()

    private val _option4 = MutableStateFlow("option 4")
    val option4 = _option4.asStateFlow()

    private val _showResult = MutableStateFlow(false)
    val showResult = _showResult.asStateFlow()

    private val _isCorrectAnswer = MutableStateFlow(false)
    val isCorrectAnswer = _isCorrectAnswer.asStateFlow()

    private val _correctAnswer = MutableStateFlow("Correct Hero Name")
    val correctAnswer = _correctAnswer.asStateFlow()

    private val _chosenHero = MutableStateFlow("Chosen hero name")
    val chosenHero = _chosenHero.asStateFlow()

    init {
        viewModelScope.launch {
            orientationDataManager.getSensorData().collect {
                _sensorData.value = it
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            gameInit()
        }
    }

    fun newGame() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            _showResult.value = false
            gameInit()
        }
    }

    private suspend fun gameInit() {
        game = repo.getNewQuizGame()
        _heroPortraitUrl.value = game.correctAnswer.image.url
        _option1.value = game.option1.name
        _option2.value = game.option2.name
        _option3.value = game.option3.name
        _option4.value = game.option4.name
        _correctAnswer.value = game.correctAnswer.name
        _isLoading.value = false
    }

    fun selectOption1() {
        selectOption(QuizOption.OPTION_1)
    }

    fun selectOption2() {
        selectOption(QuizOption.OPTION_2)
    }

    fun selectOption3() {
        selectOption(QuizOption.OPTION_3)
    }

    fun selectOption4() {
        selectOption(QuizOption.OPTION_4)
    }

    private fun selectOption(option: QuizOption) {
        _isCorrectAnswer.value = game.isCorrectAnswer(option)
        _chosenHero.value = game.selectedAnswer
        _showResult.value = true
    }

    fun cancelSensorDataFlow() {
        orientationDataManager.cancel()
    }
    override fun onCleared() {
        super.onCleared()
        orientationDataManager.cancel()
    }
}
