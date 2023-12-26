package ar.edu.unlam.mobile.scaffold.ui.screens.quiz

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.DefaultDataManager
import ar.edu.unlam.mobile.scaffold.data.database.HeroDataBase
import ar.edu.unlam.mobile.scaffold.data.database.dao.HeroDao
import ar.edu.unlam.mobile.scaffold.data.network.HeroService
import ar.edu.unlam.mobile.scaffold.data.network.IHeroApiClient
import ar.edu.unlam.mobile.scaffold.data.repository.herorepository.HeroRepository
import ar.edu.unlam.mobile.scaffold.data.repository.quizrepository.QuizGameRepository
import ar.edu.unlam.mobile.scaffold.ui.theme.ComicWarTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(AndroidJUnit4::class)
@LargeTest
class QuizScreenKtTest {

    // compose rule is required to get access to the composable component
    @get: Rule
    val composeTestRule = createComposeRule()

    lateinit var heroRepository: HeroRepository
    lateinit var heroDao: HeroDao
    lateinit var heroDataBase: HeroDataBase
    lateinit var quizRepo: QuizGameRepository
    lateinit var heroService: HeroService

    @Before
    fun setup() {
        heroDataBase = Room.inMemoryDatabaseBuilder(
            context = ApplicationProvider.getApplicationContext(),
            klass = HeroDataBase::class.java,
        ).build()

        heroDao = heroDataBase.getHeroDao()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://superheroapi.com/api/10160635333444116/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IHeroApiClient::class.java)

        heroService = HeroService(retrofit)

        heroRepository = HeroRepository(
            api = heroService,
            dataBase = heroDao
        )

        quizRepo = QuizGameRepository(heroRepo = heroRepository)
    }

    @Test
    fun whenHavingDefaultStates_VerifyIfAllViewsExists() {
        composeTestRule.setContent { // setting our composable as content for test
            ComicWarTheme {
                QuizUi(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        composeTestRule.onNodeWithTag("loading composable").assertDoesNotExist()
        composeTestRule.onNodeWithTag("background image").assertExists()
        composeTestRule.onNodeWithTag("title").assertExists()
        composeTestRule.onNodeWithTag("hero image").assertExists()
        composeTestRule.onNodeWithTag("answer panel").assertExists()
        composeTestRule.onNodeWithTag("Result popup").assertDoesNotExist()
    }

    @Test
    fun whenItsLoadingVerifyLoadingComposableExists() {
        composeTestRule.setContent { // setting our composable as content for test
            ComicWarTheme {
                QuizUi(
                    modifier = Modifier.fillMaxSize(),
                    isLoading = true
                )
            }
        }
        composeTestRule.onNodeWithTag("loading composable").assertExists()
        composeTestRule.onNodeWithTag("background image").assertExists()
        composeTestRule.onNodeWithTag("title").assertDoesNotExist()
        composeTestRule.onNodeWithTag("hero image").assertDoesNotExist()
        composeTestRule.onNodeWithTag("answer panel").assertDoesNotExist()
        composeTestRule.onNodeWithTag("Result popup").assertDoesNotExist()
    }

    @Test
    fun whenShowPopupIsTrue_VerifyResultPopupExists() {
        composeTestRule.setContent { // setting our composable as content for test
            ComicWarTheme {
                QuizUi(
                    modifier = Modifier.fillMaxSize(),
                    showPopup = { true }
                )
            }
        }
        composeTestRule.onNodeWithTag("Result popup").assertExists()
    }

    @Test
    fun whenShowPopupIsFalse_VerifyResultPopupDoesNotExist() {
        composeTestRule.setContent { // setting our composable as content for test
            ComicWarTheme {
                QuizUi(
                    modifier = Modifier.fillMaxSize(),
                    showPopup = { false }
                )
            }
        }
        composeTestRule.onNodeWithTag("Result popup").assertDoesNotExist()
    }

    @Test
    fun whenShowIsTrueInQuizPopupResult_VerifyAllViewsExist() {
        composeTestRule.setContent {
            ComicWarTheme {
                QuizResultPopup(
                    show = { true }
                )
            }
        }
        composeTestRule.onNodeWithTag("alert dialog").assertExists()
        composeTestRule.onNodeWithTag("body").assertExists()
        composeTestRule.onNodeWithTag("play again button").assertExists()
        composeTestRule.onNodeWithTag("return to main menu button").assertExists()
    }

    @Test
    fun verifyAllViewsExistsInAnswerPanel() {
        composeTestRule.setContent {
            ComicWarTheme {
                AnswerPanel()
            }
        }
        composeTestRule.onNodeWithTag("option 1 button").assertExists()
        composeTestRule.onNodeWithTag("option 2 button").assertExists()
        composeTestRule.onNodeWithTag("option 3 button").assertExists()
        composeTestRule.onNodeWithTag("option 4 button").assertExists()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun whenClickingOption1_TheAlertDialogMustExist() {
        quizRepo = QuizGameRepository(heroRepository)
        composeTestRule.setContent {
            ComicWarTheme {
                QuizScreen(
                    viewModel = QuizViewModel(
                        repo = quizRepo,
                        orientationDataManager = DefaultDataManager()
                    )
                )
            }
        }
        composeTestRule.waitUntilNodeCount(matcher = hasClickAction(), count = 4, timeoutMillis = 10000L)
        composeTestRule.onAllNodes(
            matcher = hasClickAction(),
            useUnmergedTree = true
        )
            .printToLog(tag = "compose_test", maxDepth = 10)
        composeTestRule.onNodeWithTag(testTag = "option 1 button", useUnmergedTree = true).performClick()
        composeTestRule.onAllNodes(
            matcher = hasClickAction(),
            useUnmergedTree = true
        )
            .printToLog(tag = "copose_test", maxDepth = 10)

        composeTestRule.waitUntilExactlyOneExists(
            matcher = hasTestTag(testTag = "Result popup"),
            timeoutMillis = 8_165L
        )
        composeTestRule.onNodeWithTag("Result popup", useUnmergedTree = true).assertExists()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun whenClickingOption2_TheAlertDialogMustExist() {
        composeTestRule.setContent {
            ComicWarTheme {
                QuizScreen(
                    viewModel = QuizViewModel(
                        repo = quizRepo,
                        orientationDataManager = DefaultDataManager()
                    )
                )
            }
        }
        composeTestRule.waitUntilNodeCount(matcher = hasClickAction(), count = 4, timeoutMillis = 10000L)
        composeTestRule.onAllNodes(
            matcher = hasClickAction(),
            useUnmergedTree = true
        )
            .printToLog(tag = "compose_test", maxDepth = 10)
        composeTestRule.onNodeWithTag(testTag = "option 2 button", useUnmergedTree = true).performClick()
        composeTestRule.onAllNodes(
            matcher = hasClickAction(),
            useUnmergedTree = true
        )
            .printToLog(tag = "copose_test", maxDepth = 10)

        composeTestRule.waitUntilExactlyOneExists(
            matcher = hasTestTag(testTag = "Result popup"),
            timeoutMillis = 8_165L
        )
        composeTestRule.onNodeWithTag("Result popup", useUnmergedTree = true).assertExists()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun whenClickingOption3_TheAlertDialogMustExist() {
        composeTestRule.setContent {
            ComicWarTheme {
                QuizScreen(
                    viewModel = QuizViewModel(
                        repo = quizRepo,
                        orientationDataManager = DefaultDataManager()
                    )
                )
            }
        }
        composeTestRule.waitUntilNodeCount(matcher = hasClickAction(), count = 4, timeoutMillis = 10000L)
        composeTestRule.onAllNodes(
            matcher = hasClickAction(),
            useUnmergedTree = true
        )
            .printToLog(tag = "compose_test", maxDepth = 10)
        composeTestRule.onNodeWithTag(testTag = "option 3 button", useUnmergedTree = true).performClick()
        composeTestRule.onAllNodes(
            matcher = hasClickAction(),
            useUnmergedTree = true
        )
            .printToLog(tag = "copose_test", maxDepth = 10)

        composeTestRule.waitUntilExactlyOneExists(
            matcher = hasTestTag(testTag = "Result popup"),
            timeoutMillis = 8_165L
        )
        composeTestRule.onNodeWithTag("Result popup", useUnmergedTree = true).assertExists()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun whenClickingOption4_TheAlertDialogMustExist() {
        composeTestRule.setContent {
            ComicWarTheme {
                QuizScreen(
                    viewModel = QuizViewModel(
                        repo = quizRepo,
                        orientationDataManager = DefaultDataManager()
                    )
                )
            }
        }
        composeTestRule.waitUntilNodeCount(matcher = hasClickAction(), count = 4, timeoutMillis = 10000L)
        composeTestRule.onAllNodes(
            matcher = hasClickAction(),
            useUnmergedTree = true
        )
            .printToLog(tag = "compose_test", maxDepth = 10)
        composeTestRule.onNodeWithTag(testTag = "option 4 button", useUnmergedTree = true).performClick()
        composeTestRule.onAllNodes(
            matcher = hasClickAction(),
            useUnmergedTree = true
        )
            .printToLog(tag = "copose_test", maxDepth = 10)

        composeTestRule.waitUntilExactlyOneExists(
            matcher = hasTestTag(testTag = "Result popup"),
            timeoutMillis = 8_165L
        )
        composeTestRule.onNodeWithTag("Result popup", useUnmergedTree = true).assertExists()
    }
}
