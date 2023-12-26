package ar.edu.unlam.mobile.scaffold.domain.cardgame

import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import ar.edu.unlam.mobile.scaffold.domain.model.StatModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CardGame(
    val playerDeck: List<HeroModel>,
    val adversaryDeck: List<HeroModel>
) {
    private val _winner = MutableStateFlow(Winner.NONE)
    val winner = _winner.asStateFlow()

    private val _canMultix2BeUsed = MutableStateFlow(true)
    val canMultix2BeUsed = _canMultix2BeUsed.asStateFlow()

    private val _playerScore = MutableStateFlow(0)
    val playerScore = _playerScore.asStateFlow()

    private val _adversaryScore = MutableStateFlow(0)
    val adversaryScore = _adversaryScore.asStateFlow()

    private val _currentPlayerDeck = MutableStateFlow(playerDeck)
    val currentPlayerDeck = _currentPlayerDeck.asStateFlow()

    private val _lastCardFightWinner = MutableStateFlow(Winner.NONE)
    val lastCardFightWinner = _lastCardFightWinner.asStateFlow()

    private var currentAdversaryDeck: MutableList<HeroModel> = adversaryDeck.toMutableList()

    private val _currentAdversaryCard = MutableStateFlow(adversaryDeck[0])
    val currentAdversaryCard = _currentAdversaryCard.asStateFlow()

    fun playerPlayCard(id: Int, stat: Stat, useMultix2: Boolean) {
        val playerCard = _currentPlayerDeck.value.find { it.id == id }
        var playerCardStat = getStat(stats = playerCard!!.stats, stat = stat)
        val adversaryCardStat = getStat(stats = _currentAdversaryCard.value.stats, stat = stat)

        playerCardStat = applyMultiply(playerCardStat, useMultix2)

        if (playerCardStat > adversaryCardStat) {
            playerCardWon(playerCardStat, adversaryCardStat)
        } else {
            adversaryCardWon(
                playerCardId = id,
                playerCardStat = playerCardStat,
                adversaryCardStat = adversaryCardStat
            )
        }
    }

    private fun playerCardWon(playerCardStat: Int, adversaryCardStat: Int) {
        _lastCardFightWinner.value = Winner.PLAYER
        _playerScore.value += playerCardStat - adversaryCardStat
        currentAdversaryDeck.remove(_currentAdversaryCard.value)
        if (currentAdversaryDeck.isEmpty()) {
            calculateWinner()
        } else {
            _currentAdversaryCard.value = currentAdversaryDeck[0]
        }
    }

    private fun applyMultiply(playerCardStat: Int, useMultix2: Boolean): Int {
        return if (useMultix2 && _canMultix2BeUsed.value) {
            _canMultix2BeUsed.value = false
            playerCardStat * 2
        } else {
            playerCardStat
        }
    }

    private fun adversaryCardWon(
        playerCardId: Int,
        playerCardStat: Int,
        adversaryCardStat: Int
    ) {
        _lastCardFightWinner.value = Winner.ADVERSARY
        _adversaryScore.value += adversaryCardStat - playerCardStat
        _currentPlayerDeck.value = _currentPlayerDeck.value.filter { it.id != playerCardId }
        if (_currentPlayerDeck.value.isEmpty()) {
            calculateWinner()
        }
    }

    private fun calculateWinner() {
        _winner.value = if (_playerScore.value > _adversaryScore.value) {
            Winner.PLAYER
        } else {
            Winner.ADVERSARY
        }
    }

    private fun getStat(stats: StatModel, stat: Stat): Int {
        return when (stat) {
            Stat.COMBAT -> stats.combat
            Stat.DURABILITY -> stats.durability
            Stat.INTELLIGENCE -> stats.intelligence
            Stat.POWER -> stats.power
            Stat.SPEED -> stats.speed
            Stat.STRENGTH -> stats.strength
        }
    }
}
