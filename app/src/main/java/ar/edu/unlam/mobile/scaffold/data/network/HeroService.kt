package ar.edu.unlam.mobile.scaffold.data.network

import ar.edu.unlam.mobile.scaffold.data.network.model.HeroApiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeroService @Inject constructor(private val api: IHeroApiClient) {

    private val LOWEST_HERO_ID = 1
    private val HIGEST_HERO_ID = 731
    suspend fun getHero(id: Int): HeroApiModel {
        if (id < LOWEST_HERO_ID || id > HIGEST_HERO_ID) {
            throw HeroServiceException(message = "Hero id out of range [$LOWEST_HERO_ID,$HIGEST_HERO_ID].")
        }
        return withContext(Dispatchers.IO) {
            val response = api.getHeroResponse(id.toString())
            response.body() ?: HeroApiModel()
        }
    }
}
