package ar.edu.unlam.mobile.scaffold.data.repository.herorepository

import ar.edu.unlam.mobile.scaffold.domain.model.DeckModel
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import kotlinx.coroutines.flow.Flow

interface IHeroRepository {

    val COLLECTION_MAX_SIZE: Int

    suspend fun getRandomPlayerDeck(size: Int): List<HeroModel>
    suspend fun getAdversaryDeck(size: Int): List<HeroModel>
    suspend fun getHero(heroId: Int): HeroModel
    suspend fun getAllHero(): List<HeroModel>
    fun preloadHeroCache(): Flow<Float>
    suspend fun getRandomDeck(): DeckModel
    suspend fun winHeroCard(): HeroModel
    suspend fun winHeroCard(id: Int): HeroModel
}
