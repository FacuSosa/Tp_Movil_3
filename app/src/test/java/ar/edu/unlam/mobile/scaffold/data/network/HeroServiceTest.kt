package ar.edu.unlam.mobile.scaffold.data.network

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HeroServiceTest {
    /*
        JUnit 4 exposes a rule-based API to allow for some automation following the test lifecycle.
        MockK includes a rule which uses this to set up and tear down your mocks without needing to
        manually call MockKAnnotations.init(this).
     */
    @get:Rule
    val mockkRule = MockKRule(this)

    /*
        Esta regla es para que se ejecute cada línea de código de forma secuencial.
        Nos sirve en casos en donde se necesite el uso de livedata, flow, suspend functions, etc.
     */
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var heroService: HeroService

    @Before
    fun setup() {
        val api = Retrofit.Builder()
            .baseUrl("https://superheroapi.com/api/10160635333444116/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IHeroApiClient::class.java)
        heroService = HeroService(api)
    }

    @Test
    fun getHeroFromApiProvidingCorrectHeroId() = runTest {
        val heroId = 1
        val expectedCharacterName = "A-Bomb"
        val hero = heroService.getHero(heroId)
        assertThat(hero.name).isEqualTo(expectedCharacterName)
    }

    @Test(expected = HeroServiceException::class)
    fun throwHeroServiceExceptionWhenProvidingHeroIdLowerThanOne() = runTest {
        val heroId = 0
        heroService.getHero(heroId)
    }

    @Test(expected = HeroServiceException::class)
    fun throwHeroServiceExceptionWhenProvidingHeroIdHigherThan731() = runTest {
        val heroId = 732
        heroService.getHero(heroId)
    }
}
