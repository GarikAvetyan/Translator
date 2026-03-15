package app.translator.domain.usecase

import app.translator.domain.model.GrammarLesson
import app.translator.domain.repository.LearnRepository

class GetGrammarLessonsUseCase(
    private val repository: LearnRepository
) {
    operator fun invoke(): List<GrammarLesson> = repository.getGrammarLessons()
}
