package ar.edu.unlam.mobile.scaffold.domain.quiz

import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel

class QuizGame(private val heroList: List<HeroModel>) {
    lateinit var correctAnswer: HeroModel
        private set
    lateinit var option1: HeroModel
        private set
    lateinit var option2: HeroModel
        private set
    lateinit var option3: HeroModel
        private set
    lateinit var option4: HeroModel
        private set

    var selectedAnswer = "Hero name"
        private set

    init {
        selectCorrectAnswer()
        selectPossibleAnswers()
    }

    private fun selectCorrectAnswer() {
        correctAnswer = heroList.random()
    }

    private fun selectPossibleAnswers() {
        val list = heroList.toMutableList()
        option1 = list.random()
        list.remove(option1)
        option2 = list.random()
        list.remove(option2)
        option3 = list.random()
        list.remove(option3)
        option4 = list[0]
    }

    fun isCorrectAnswer(option: QuizOption): Boolean {
        return when (option) {
            QuizOption.OPTION_1 -> {
                selectedAnswer = option1.name
                correctAnswer.id == option1.id
            }
            QuizOption.OPTION_2 -> {
                selectedAnswer = option2.name
                correctAnswer.id == option2.id
            }
            QuizOption.OPTION_3 -> {
                selectedAnswer = option3.name
                correctAnswer.id == option3.id
            }
            QuizOption.OPTION_4 -> {
                selectedAnswer = option4.name
                correctAnswer.id == option4.id
            }
        }
    }
}
