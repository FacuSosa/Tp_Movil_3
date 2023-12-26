package ar.edu.unlam.mobile.scaffold.ui.screens.quiz

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ar.edu.unlam.mobile.scaffold.MainDispatcherRule
import ar.edu.unlam.mobile.scaffold.core.sensor.sensordatamanager.IOrientationDataManager
import ar.edu.unlam.mobile.scaffold.data.repository.quizrepository.IQuizGameRepository
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import ar.edu.unlam.mobile.scaffold.domain.model.ImageModel
import ar.edu.unlam.mobile.scaffold.domain.quiz.QuizGame
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class QuizViewModelTest {

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
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule() // leer la descripción de la clase

    /*
        @relaxedmockk
        se pueden mockear manualmente los métodos que necesitamos y el resto se genera
        automáticamente.

        @mockk
        debemos generar manualmente una respuesta para todos los métodos.
     */

    @RelaxedMockK
    lateinit var quizRepo: IQuizGameRepository

    @RelaxedMockK
    lateinit var sensorDataManager: IOrientationDataManager

    private lateinit var viewModel: QuizViewModel

    private val heroList = listOf(
        HeroModel(id = 1, name = "Mr test 1", image = ImageModel("https://loremflickr.com/400/400/cat?lock=1")),
        HeroModel(id = 2, name = "Mr test 2", image = ImageModel("https://loremflickr.com/400/400/dog?lock=1")),
        HeroModel(id = 3, name = "Mr test 3", image = ImageModel("https://loremflickr.com/400/400/mouse?lock=1")),
        HeroModel(id = 4, name = "Mr test 4", image = ImageModel("https://loremflickr.com/400/400/cow?lock=1"))
    )

    @Before
    fun setUp() {
        coEvery { quizRepo.getNewQuizGame() } returns QuizGame(heroList)
        viewModel = QuizViewModel(quizRepo, sensorDataManager)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test(timeout = 1500L)
    fun `when the viewmodel is instanced, the loading shouldn't take more than 1500 milliseconds`() = runTest {
        while (viewModel.isLoading.value) {
            // wait till the viewmodel is fully loaded
            // or the test fails because it reached the timeout limit
        }
        val value = viewModel.isLoading.value
        assertThat(value).isFalse()
    }

    @Test
    fun `after viewmodel finishes loading, PortraitUrl should be from heroList`() = runTest {
        val portraitUrlList = heroList.map { it.image.url }
        while (viewModel.isLoading.value) {
            // wait till it finishes loading
        }
        val url = viewModel.heroPortraitUrl.value
        assertThat(url).isNotEmpty()
        assertThat(url).isIn(portraitUrlList)
    }

    @Test
    fun `after viewmodel finishes loading, the options must be hero names from heroList`() =
        runTest {
            while (viewModel.isLoading.value) {
                // wait till it finishes loading
            }
            val nameList = mutableListOf<String>()
            for (hero in heroList) {
                nameList.add(hero.name)
            }
            val heroName1 = viewModel.option1.value
            val heroName2 = viewModel.option2.value
            val heroName3 = viewModel.option3.value
            val heroName4 = viewModel.option4.value

            assertThat(heroName1).isIn(nameList)
            assertThat(heroName2).isIn(nameList)
            assertThat(heroName3).isIn(nameList)
            assertThat(heroName4).isIn(nameList)
        }

    @Test
    fun `after viewmodel finishes loading, showResult must be false`() = runTest {
        while (viewModel.isLoading.value) {
            // wait till it finishes loading
        }
        assertThat(viewModel.showResult.value).isFalse()
    }

    @Test
    fun `after using  selectOption1(), showResult must be true`() = runTest {
        while (viewModel.isLoading.value) {
            // wait till it finishes loading
        }
        viewModel.selectOption1()
        assertThat(viewModel.showResult.value).isTrue()
    }

    @Test
    fun `after using  selectOption2(), showResult must be true`() = runTest {
        while (viewModel.isLoading.value) {
            // wait till it finishes loading
        }
        viewModel.selectOption2()
        assertThat(viewModel.showResult.value).isTrue()
    }

    @Test
    fun `after using  selectOption3(), showResult must be true`() = runTest {
        while (viewModel.isLoading.value) {
            // wait till it finishes loading
        }
        viewModel.selectOption3()
        assertThat(viewModel.showResult.value).isTrue()
    }

    @Test
    fun `after using  selectOption4(), showResult must be true`() = runTest {
        while (viewModel.isLoading.value) {
            // wait till it finishes loading
        }
        viewModel.selectOption4()
        assertThat(viewModel.showResult.value).isTrue()
    }

    @Test
    fun `when selectOption1(), if its correct correctAnswer must be equal to option1,value, else should be different`() =
        runTest {
            while (viewModel.isLoading.value) {
                // wait till it finishes loading
            }
            viewModel.selectOption1()
            if (viewModel.isCorrectAnswer.value) {
                assertThat(viewModel.correctAnswer.value).isEqualTo(viewModel.option1.value)
            } else {
                assertThat(viewModel.correctAnswer.value).isNotEqualTo(viewModel.option1.value)
            }
        }

    @Test
    fun `when selectOption2(), if its correct correctAnswer must be equal to option1,value, else should be different`() =
        runTest {
            while (viewModel.isLoading.value) {
                // wait till it finishes loading
            }
            viewModel.selectOption2()
            if (viewModel.isCorrectAnswer.value) {
                assertThat(viewModel.correctAnswer.value).isEqualTo(viewModel.option2.value)
            } else {
                assertThat(viewModel.correctAnswer.value).isNotEqualTo(viewModel.option2.value)
            }
        }

    @Test
    fun `when selectOption3(), if its correct correctAnswer must be equal to option1,value, else should be different`() =
        runTest {
            while (viewModel.isLoading.value) {
                // wait till it finishes loading
            }
            viewModel.selectOption3()
            if (viewModel.isCorrectAnswer.value) {
                assertThat(viewModel.correctAnswer.value).isEqualTo(viewModel.option3.value)
            } else {
                assertThat(viewModel.correctAnswer.value).isNotEqualTo(viewModel.option3.value)
            }
        }

    @Test
    fun `when selectOption4(), if its correct correctAnswer must be equal to option1,value, else should be different`() =
        runTest {
            while (viewModel.isLoading.value) {
                // wait till it finishes loading
            }
            viewModel.selectOption4()
            if (viewModel.isCorrectAnswer.value) {
                assertThat(viewModel.correctAnswer.value).isEqualTo(viewModel.option4.value)
            } else {
                assertThat(viewModel.correctAnswer.value).isNotEqualTo(viewModel.option4.value)
            }
        }

    @Test
    fun `when selectOption1(), then chosenHero must be option1,value`() = runTest {
        while (viewModel.isLoading.value) {
            // wait till it finishes loading
        }
        viewModel.selectOption1()
        assertThat(viewModel.chosenHero.value).isEqualTo(viewModel.option1.value)
    }

    @Test
    fun `when selectOption2(), then chosenHero must be option2,value`() = runTest {
        while (viewModel.isLoading.value) {
            // wait till it finishes loading
        }
        viewModel.selectOption2()
        assertThat(viewModel.chosenHero.value).isEqualTo(viewModel.option2.value)
    }

    @Test
    fun `when selectOption3(), then chosenHero must be option3,value`() = runTest {
        while (viewModel.isLoading.value) {
            // wait till it finishes loading
        }
        viewModel.selectOption3()
        assertThat(viewModel.chosenHero.value).isEqualTo(viewModel.option3.value)
    }

    @Test
    fun `when selectOption4(), then chosenHero must be option4,value`() = runTest {
        while (viewModel.isLoading.value) {
            // wait till it finishes loading
        }
        viewModel.selectOption4()
        assertThat(viewModel.chosenHero.value).isEqualTo(viewModel.option4.value)
    }
}
