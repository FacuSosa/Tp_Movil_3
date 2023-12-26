package ar.edu.unlam.mobile.scaffold.data.repository.quizrepository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ar.edu.unlam.mobile.scaffold.data.repository.herorepository.IHeroRepository
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
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

class QuizGameRepositoryTest {

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

    @RelaxedMockK
    lateinit var repo: IHeroRepository

    lateinit var quizRepo: IQuizGameRepository

    private val heroList = listOf(
        HeroModel(id = 1, name = "Mr test 1"),
        HeroModel(id = 2, name = "Mr test 2"),
        HeroModel(id = 3, name = "Mr test 3"),
        HeroModel(id = 4, name = "Mr test 4")
    )

    @Before
    fun setUp() {
        coEvery { repo.getRandomPlayerDeck(4) } returns heroList
        quizRepo = QuizGameRepository(repo)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `get new quiz game with correct values`() = runTest {
        val game = quizRepo.getNewQuizGame()
        assertThat(game.correctAnswer).isIn(heroList)
        assertThat(game.option1).isIn(heroList)
        assertThat(game.option2).isIn(heroList)
        assertThat(game.option3).isIn(heroList)
        assertThat(game.option4).isIn(heroList)
    }
}
