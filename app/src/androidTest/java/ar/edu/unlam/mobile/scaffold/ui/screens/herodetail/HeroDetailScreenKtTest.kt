package ar.edu.unlam.mobile.scaffold.ui.screens.herodetail

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.DefaultDataManager
import ar.edu.unlam.mobile.scaffold.data.database.HeroDataBase
import ar.edu.unlam.mobile.scaffold.data.network.HeroService
import ar.edu.unlam.mobile.scaffold.data.network.IHeroApiClient
import ar.edu.unlam.mobile.scaffold.data.repository.herorepository.HeroRepository
import ar.edu.unlam.mobile.scaffold.ui.theme.ComicWarTheme
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HeroDetailScreenKtTest {

    @get: Rule
    val composeRule = createComposeRule()

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun afterHeroDetailScreenFinishesLoading_VerifyAllViewsExist() {
        val heroDataBase = Room.inMemoryDatabaseBuilder(
            context = ApplicationProvider.getApplicationContext(),
            klass = HeroDataBase::class.java,
        ).build()

        val heroDao = heroDataBase.getHeroDao()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://superheroapi.com/api/10160635333444116/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IHeroApiClient::class.java)

        val heroService = HeroService(retrofit)

        val heroRepository = HeroRepository(
            api = heroService,
            dataBase = heroDao
        )

        composeRule.setContent {
            ComicWarTheme {
                HeroDetailScreen(
                    viewModel = HeroDetailViewModelImp(heroRepository, DefaultDataManager())
                )
            }
        }
        composeRule.waitUntilDoesNotExist(
            matcher = hasTestTag("heroDetailUi Loading"),
            timeoutMillis = 8_000L
        )
        composeRule.onNodeWithTag("heroDetailUi background").assertExists()
        composeRule.onNodeWithTag("heroDetailUi profile image").assertExists()
        composeRule.onNodeWithTag("heroDetailUi character id and name").assertExists()
        composeRule.onNodeWithTag("heroDetailUi navigate to qr button").assertExists()
        composeRule.onNodeWithTag("heroDetailUi hero data").assertExists()
        // faltaría verificar si aparecen todos los datos del personaje con id 1
    }

    @Test
    fun whenProvidingDefaultStateToHeroDetailUi_VerifyAllViewsExist() {
        composeRule.setContent {
            ComicWarTheme {
                HeroDetailUi()
            }
        }
        composeRule.onNodeWithTag("heroDetailUi background").assertExists()
        composeRule.onNodeWithTag("heroDetailUi Loading").assertDoesNotExist()
        composeRule.onNodeWithTag("heroDetailUi profile image").assertExists()
        composeRule.onNodeWithTag("heroDetailUi character id and name").assertExists()
        composeRule.onNodeWithTag("heroDetailUi navigate to qr button").assertExists()
        composeRule.onNodeWithTag("heroDetailUi hero data").assertExists()
    }

    @Test
    fun whenProvidingIsLoadingTrueToHeroDetailUi_VerifyAllViewsExist() {
        composeRule.setContent {
            ComicWarTheme {
                HeroDetailUi(isLoading = true)
            }
        }
        composeRule.onNodeWithTag("heroDetailUi background").assertExists()
        composeRule.onNodeWithTag("heroDetailUi Loading").assertExists()
        composeRule.onNodeWithTag("heroDetailUi profile image").assertDoesNotExist()
        composeRule.onNodeWithTag("heroDetailUi character id and name").assertDoesNotExist()
        composeRule.onNodeWithTag("heroDetailUi navigate to qr button").assertDoesNotExist()
        composeRule.onNodeWithTag("heroDetailUi hero data").assertDoesNotExist()
    }

    @Test
    fun whenProvidingDefaultStateToHeroData_VerifyAllViewsExist() {
        composeRule.setContent {
            ComicWarTheme {
                HeroData()
            }
        }
        composeRule.onNodeWithTag("title stats").assertExists()
        composeRule.onNodeWithTag("stat text").assertExists()
        composeRule.onNodeWithTag("title biography").assertExists()
        composeRule.onNodeWithTag("biography text").assertExists()
        composeRule.onNodeWithTag("title appearance").assertExists()
        composeRule.onNodeWithTag("heroData appearance text").assertExists()
        composeRule.onNodeWithTag("title profession").assertExists()
        composeRule.onNodeWithTag("heroData profession text").assertExists()
        composeRule.onNodeWithTag("title connections").assertExists()
        composeRule.onNodeWithTag("heroData text connections").assertExists()
    }
}
