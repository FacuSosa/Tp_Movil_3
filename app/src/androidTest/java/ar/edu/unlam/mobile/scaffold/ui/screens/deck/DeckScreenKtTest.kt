package ar.edu.unlam.mobile.scaffold.ui.screens.deck

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import ar.edu.unlam.mobile.scaffold.data.database.HeroDataBase
import ar.edu.unlam.mobile.scaffold.data.network.HeroService
import ar.edu.unlam.mobile.scaffold.data.network.IHeroApiClient
import ar.edu.unlam.mobile.scaffold.data.repository.herorepository.HeroRepository
import ar.edu.unlam.mobile.scaffold.ui.theme.ComicWarTheme
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DeckScreenKtTest {

    @get: Rule
    val ruleCompose = createComposeRule()

    @Test
    fun testQueCompruebaQueExistanTodosLosComponentesDeDeckScreen() {

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

        ruleCompose.setContent {
            ComicWarTheme{
                DeckScreen(deckViewModel = DeckViewModel(heroRepository))
            }
        }
        //ruleCompose.onNodeWithTag(testTag = "TestDeckScreen pantalla fondo").assertExists()
        ruleCompose.onNodeWithTag(testTag = "DeckScreen titulo pantalla").assertExists()
        //ruleCompose.onNodeWithTag(testTag = "DeckScreen Hero Card").assertExists()
    }
}