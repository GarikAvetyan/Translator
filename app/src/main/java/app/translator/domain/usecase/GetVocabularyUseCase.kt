package app.translator.domain.usecase

import app.translator.domain.model.VocabularyWord
import app.translator.domain.repository.LearnRepository
import javax.inject.Inject

class GetVocabularyUseCase @Inject constructor(
    private val repository: LearnRepository
) {
    operator fun invoke(): List<VocabularyWord> = repository.getVocabularyWords()
}
