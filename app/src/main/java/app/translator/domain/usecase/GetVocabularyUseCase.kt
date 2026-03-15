package app.translator.domain.usecase

import app.translator.domain.model.VocabularyWord
import app.translator.domain.repository.LearnRepository

class GetVocabularyUseCase(
    private val repository: LearnRepository
) {
    operator fun invoke(): List<VocabularyWord> = repository.getVocabularyWords()
}
