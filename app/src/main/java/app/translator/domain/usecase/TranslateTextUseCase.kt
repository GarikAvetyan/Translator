package app.translator.domain.usecase

import app.translator.domain.model.TranslationResult
import app.translator.domain.repository.TranslationRepository
import javax.inject.Inject

class TranslateTextUseCase @Inject constructor(
    private val repository: TranslationRepository
) {
    suspend operator fun invoke(
        text: String,
        source: String,
        target: String
    ): TranslationResult {
        return repository.translate(text, source, target)
    }
}
