package ar.edu.unlam.mobile.scaffold.data.repository.herorepository

import ar.edu.unlam.mobile.scaffold.data.database.dao.HeroDao
import ar.edu.unlam.mobile.scaffold.data.database.entities.HeroEntity
import ar.edu.unlam.mobile.scaffold.data.database.entities.HeroQuantityUpdate
import ar.edu.unlam.mobile.scaffold.data.database.entities.toHeroModel
import ar.edu.unlam.mobile.scaffold.data.network.HeroService
import ar.edu.unlam.mobile.scaffold.data.network.model.HeroApiModel
import ar.edu.unlam.mobile.scaffold.data.network.model.Powerstats
import ar.edu.unlam.mobile.scaffold.data.network.model.toHeroEntityModel
import ar.edu.unlam.mobile.scaffold.data.network.model.toHeroModel
import ar.edu.unlam.mobile.scaffold.domain.model.DeckModel
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HeroRepository @Inject constructor(private val api: HeroService, private val dataBase: HeroDao) :
    IHeroRepository {

    // private val API_COLLECTION_SIZE = 731 // no eliminar
    override val COLLECTION_MAX_SIZE = 731 // es menor o igual a API_COLLECTION_SIZE
    override fun preloadHeroCache(): Flow<Float> {
        return flow {
            emit(0f)
            for (i in 1..COLLECTION_MAX_SIZE) {
                getHero(i)
                val percentage = i / COLLECTION_MAX_SIZE.toFloat()
                emit(percentage)
            }
        }
    }

    override suspend fun winHeroCard(): HeroModel {
        val id = (1..COLLECTION_MAX_SIZE).random()
        return updateHeroQuantity(id)
    }

    private suspend fun updateHeroQuantity(
        id: Int
    ): HeroModel {
        val hero = getHero(id)
        val updateHero = HeroQuantityUpdate(
            id = hero.id,
            quantity = hero.quantity + 1
        )
        dataBase.updateQuantity(updateHero)
        return getHero(id)
    }

    override suspend fun winHeroCard(id: Int): HeroModel {
        return updateHeroQuantity(id)
    }

    override suspend fun getAdversaryDeck(size: Int): List<HeroModel> {
        return getRandomPlayerDeck(size)
    }

    override suspend fun getRandomPlayerDeck(size: Int): List<HeroModel> {
        val list = mutableListOf<HeroModel>()
        return withContext(Dispatchers.IO) {
            for (i in 1..size) {
                var randomId = (1..COLLECTION_MAX_SIZE).random()
                val isIdInList = { list.isNotEmpty() && (list.find { it.id == randomId } != null) }
                while (isIdInList()) {
                    randomId = (1..COLLECTION_MAX_SIZE).random()
                }
                val hero = getHero(randomId)
                list.add(hero)
            }
            list
        }
    }

    override suspend fun getRandomDeck(): DeckModel {
        val list = getRandomPlayerDeck(6)
        return DeckModel(
            id = null,
            carta1 = list[0],
            carta2 = list[1],
            carta3 = list[2],
            carta4 = list[3],
            carta5 = list[4],
            carta6 = list[5]
        )
    }
    private fun formatDataHero(h: HeroApiModel): HeroApiModel {
        return if (isPowerStatsNull(h)) convertNullPowerstatsToNotNull(h) else h
    }
    private fun isPowerStatsNull(h: HeroApiModel): Boolean {
        return h.powerstats.power == "null"
    }

    private fun convertNullPowerstatsToNotNull(h: HeroApiModel): HeroApiModel {
        return h.copy(
            powerstats = Powerstats(
                combat = "1",
                durability = "1",
                intelligence = "1",
                power = "1",
                speed = "1",
                strength = "1"
            )
        )
    }

    override suspend fun getHero(heroId: Int): HeroModel {
        if (heroId < 1 || heroId > COLLECTION_MAX_SIZE) {
            throw HeroRepositoryException("Hero id outside of range [1,$COLLECTION_MAX_SIZE].")
        }
        return withContext(Dispatchers.IO) {
            val heroDB = dataBase.getHero(heroId)
            if (heroDB != null) {
                heroDB.toHeroModel()
            } else {
                val hero = api.getHero(heroId)
                val formattedHero = formatDataHero(hero)
                dataBase.insertHero(formattedHero.toHeroEntityModel())
                formattedHero.toHeroModel()
            }
        }
    }

    override suspend fun getAllHero(): List<HeroModel> {
        val dbList = dataBase.getAll()
        val heroList = mutableListOf<HeroModel>()
        val saveToDbList = mutableListOf<HeroEntity>()

        if (dbList.isNotEmpty()) {
            for (i in 1..COLLECTION_MAX_SIZE) {
                val heroDb = dbList.find { it.id == i }
                if (heroDb != null) {
                    heroList.add(heroDb.toHeroModel())
                } else {
                    val heroApi = formatDataHero(api.getHero(i))
                    saveToDbList.add(heroApi.toHeroEntityModel())
                    heroList.add(heroApi.toHeroModel())
                }
            }
        } else {
            for (i in 1..COLLECTION_MAX_SIZE) {
                val heroApi = formatDataHero(api.getHero(i))
                saveToDbList.add(heroApi.toHeroEntityModel())
                heroList.add(heroApi.toHeroModel())
            }
        }

        if (saveToDbList.isNotEmpty()) {
            dataBase.insertAll(saveToDbList)
        }

        return heroList
    }
}
