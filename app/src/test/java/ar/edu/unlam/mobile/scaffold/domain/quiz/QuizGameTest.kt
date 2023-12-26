package ar.edu.unlam.mobile.scaffold.domain.quiz

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import com.google.common.truth.Truth.assertThat
import io.mockk.junit4.MockKRule
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class QuizGameTest {

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

    /*
        @relaxedmockk
        se pueden mockear manualmente los métodos que necesitamos y el resto se genera
        automáticamente.

        @mockk
        debemos generar manualmente una respuesta para todos los métodos.
     */

    private lateinit var quizGame: QuizGame

    @Before
    fun setup() {
        quizGame = QuizGame(heroList)
    }

    @After
    fun afterTests() {
        unmockkAll()
    }

    private val heroList = listOf(
        HeroModel(
            id = 1,
            name = "Test 1"
        ),
        HeroModel(
            id = 2,
            name = "Test 2"
        ),
        HeroModel(
            id = 3,
            name = "Test 3"
        ),
        HeroModel(
            id = 4,
            name = "Test 4"
        )
    )

    @Test
    fun `when creating a game with a hero list, the correct answer must be from the provided hero list`() =
        runTest {
            assertThat(quizGame.correctAnswer).isIn(heroList)
        }

    @Test
    fun `when creating a game with a hero list the options must be from the same list`() = runTest {
        assertThat(quizGame.option1).isIn(heroList)
        assertThat(quizGame.option2).isIn(heroList)
        assertThat(quizGame.option3).isIn(heroList)
        assertThat(quizGame.option4).isIn(heroList)
    }

    @Test
    fun `when selecting the correct answer, isCorrectAnswer() must return true`() = runTest {
        val option1 = quizGame.isCorrectAnswer(QuizOption.OPTION_1)
        val option2 = quizGame.isCorrectAnswer(QuizOption.OPTION_2)
        val option3 = quizGame.isCorrectAnswer(QuizOption.OPTION_3)
        val option4 = quizGame.isCorrectAnswer(QuizOption.OPTION_4)
        when (quizGame.correctAnswer.id) {
            quizGame.option1.id -> {
                assertThat(option1).isTrue()
                assertThat(option2).isFalse()
                assertThat(option3).isFalse()
                assertThat(option4).isFalse()
            }
            quizGame.option2.id -> {
                assertThat(option2).isTrue()
                assertThat(option1).isFalse()
                assertThat(option3).isFalse()
                assertThat(option4).isFalse()
            }
            quizGame.option3.id -> {
                assertThat(option3).isTrue()
                assertThat(option1).isFalse()
                assertThat(option2).isFalse()
                assertThat(option4).isFalse()
            }
            quizGame.option4.id -> {
                assertThat(option4).isTrue()
                assertThat(option1).isFalse()
                assertThat(option2).isFalse()
                assertThat(option3).isFalse()
            }
        }
    }
}
