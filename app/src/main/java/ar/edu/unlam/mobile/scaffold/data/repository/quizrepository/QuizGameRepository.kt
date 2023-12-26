package ar.edu.unlam.mobile.scaffold.data.repository.quizrepository

import ar.edu.unlam.mobile.scaffold.data.repository.herorepository.IHeroRepository
import ar.edu.unlam.mobile.scaffold.domain.quiz.QuizGame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class QuizGameRepository @Inject constructor(private val heroRepo: IHeroRepository) :
    IQuizGameRepository {

    private val QUIZ_OPTION_SIZE = 4
    override suspend fun getNewQuizGame(): QuizGame {
        val heroList = withContext(Dispatchers.IO) {
            heroRepo.getRandomPlayerDeck(QUIZ_OPTION_SIZE)
        }
        return QuizGame(heroList)
    }
}
