package ar.edu.unlam.mobile.scaffold.ui.screens.heroduel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffold.data.repository.herorepository.IHeroRepository
import ar.edu.unlam.mobile.scaffold.data.repository.gamerepository.IGameRepository
import ar.edu.unlam.mobile.scaffold.domain.cardgame.CardGame
import ar.edu.unlam.mobile.scaffold.domain.cardgame.Stat
import ar.edu.unlam.mobile.scaffold.domain.cardgame.Winner
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeroDuelViewModelv2 @Inject constructor(
    private val repo: IGameRepository,
    private val heroRepo: IHeroRepository
) : ViewModel() {

    private val _currentPlayerCard = MutableStateFlow(HeroModel())
    val currentPlayerCard = _currentPlayerCard.asStateFlow()

    private lateinit var game: CardGame

    lateinit var winner: StateFlow<Winner>
        private set

    lateinit var isMultiplierAvailable: StateFlow<Boolean>
        private set

    private var useMultix2 = false

    private var selectedStat = Stat.POWER

    lateinit var playerScore: StateFlow<Int>
        private set

    lateinit var adversaryScore: StateFlow<Int>
        private set

    lateinit var currentPlayerDeck: StateFlow<List<HeroModel>>
        private set

    lateinit var currentAdversaryCard: StateFlow<HeroModel>
        private set

    private val _currentScreen = MutableStateFlow(DuelScreen.SELECT_CARD_UI)
    val currentScreen = _currentScreen.asStateFlow()

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _wonHero = MutableStateFlow(HeroModel())
    val wonHero = _wonHero.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            game = repo.getNewCardGame()
            initStateFlows()
            _isLoading.value = false
        }
    }

    private suspend fun initStateFlows() {
        winner = game.winner.stateIn(scope = viewModelScope)
        isMultiplierAvailable = game.canMultix2BeUsed.stateIn(scope = viewModelScope)
        playerScore = game.playerScore.stateIn(scope = viewModelScope)
        adversaryScore = game.adversaryScore.stateIn(scope = viewModelScope)
        currentPlayerDeck = game.currentPlayerDeck.stateIn(scope = viewModelScope)
        _currentPlayerCard.value = currentPlayerDeck.value[0]
        currentAdversaryCard = game.currentAdversaryCard.stateIn(scope = viewModelScope)
    }

    fun playCard() {
        _currentScreen.value = DuelScreen.DUEL_UI
    }

    fun selectPlayerCard(cardSelectedIndex: Int) {
        _currentPlayerCard.value = currentPlayerDeck.value[cardSelectedIndex]
    }

    fun selectStat(stat: Stat) {
        selectedStat = stat
    }

    fun useMultiplierX2(use: Boolean) {
        useMultix2 = use
    }

    fun checkIfPlayerWonHero() {
        if (winner.value == Winner.PLAYER) {
            viewModelScope.launch(Dispatchers.IO) {
                _wonHero.value = heroRepo.winHeroCard()
                _currentScreen.value = DuelScreen.WIN_CARD_UI
            }
        }
    }

    fun fight() {
        val cardId = _currentPlayerCard.value.id
        game.playerPlayCard(cardId, selectedStat, useMultix2)
        useMultix2 = false
        if (winner.value == Winner.NONE) {
            if (game.lastCardFightWinner.value == Winner.ADVERSARY) {
                // se necesita resetear el estado, por eso se llama a este método con parámetro 0
                selectPlayerCard(0)
                _currentScreen.value = DuelScreen.SELECT_CARD_UI
            }
        } else {
            _currentScreen.value = DuelScreen.FINISHED_DUEL_UI
        }
    }
}
