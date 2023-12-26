package ar.edu.unlam.mobile.scaffold.data.repository.gamerepository

import ar.edu.unlam.mobile.scaffold.domain.cardgame.CardGame

interface IGameRepository {
    suspend fun getNewCardGame(): CardGame
}
