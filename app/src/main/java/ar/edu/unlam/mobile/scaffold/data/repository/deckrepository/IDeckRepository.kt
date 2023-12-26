package ar.edu.unlam.mobile.scaffold.data.repository.deckrepository

import ar.edu.unlam.mobile.scaffold.domain.model.DeckModel

interface IDeckRepository {
    suspend fun getDeckList(): List<DeckModel>
    suspend fun insertDeck(deck: DeckModel)
}
