package ar.edu.unlam.mobile.scaffold.data.repository.deckrepository

import ar.edu.unlam.mobile.scaffold.data.database.dao.DeckDao
import ar.edu.unlam.mobile.scaffold.data.database.entities.DeckEntity
import ar.edu.unlam.mobile.scaffold.data.database.entities.toEntity
import ar.edu.unlam.mobile.scaffold.data.repository.herorepository.IHeroRepository
import ar.edu.unlam.mobile.scaffold.domain.model.DeckModel
import javax.inject.Inject

class DeckRepository @Inject constructor(private val dataBase: DeckDao, private val heroRepo: IHeroRepository) :
    IDeckRepository {
    override suspend fun getDeckList(): List<DeckModel> {
        val entityList = dataBase.getAll()
        if (entityList.isNotEmpty()) {
            return convertirListaDeckEntityaDeckModel(entityList)
        }
        return emptyList()
    }

    override suspend fun insertDeck(deck: DeckModel) {
        val deckEntity = deck.toEntity()
        dataBase.insertDeck(deckEntity)
    }

    private suspend fun convertirListaDeckEntityaDeckModel(entityList: List<DeckEntity>): List<DeckModel> {
        val modelList = mutableListOf<DeckModel>()
        for (i in entityList.indices) {
            val deck = deckEntityADeckModel(entityList[i])
            modelList.add(deck)
        }
        return modelList
    }

    private suspend fun deckEntityADeckModel(entityDeck: DeckEntity): DeckModel {
        val carta1 = heroRepo.getHero(entityDeck.carta1)
        val carta2 = heroRepo.getHero(entityDeck.carta2)
        val carta3 = heroRepo.getHero(entityDeck.carta3)
        val carta4 = heroRepo.getHero(entityDeck.carta4)
        val carta5 = heroRepo.getHero(entityDeck.carta5)
        val carta6 = heroRepo.getHero(entityDeck.carta6)
        val deck = DeckModel(
            id = entityDeck.id!!,
            carta1 = carta1,
            carta2 = carta2,
            carta3 = carta3,
            carta4 = carta4,
            carta5 = carta5,
            carta6 = carta6

        )
        return deck
    }
}
