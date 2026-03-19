package app.translator.domain.usecase

import app.translator.domain.model.GrammarLesson
import app.translator.domain.repository.LearnRepository
import javax.inject.Inject

class GetGrammarLessonsUseCase @Inject constructor(
    private val repository: LearnRepository
) {
    operator fun invoke(): List<GrammarLesson> = repository.getGrammarLessons()
}
