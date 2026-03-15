package app.translator.domain.usecase

import app.translator.domain.repository.LanguagePreferencesRepository

class SaveLanguagePairUseCase(
    private val repository: LanguagePreferencesRepository
) {
    suspend operator fun invoke(sourceCode: String, targetCode: String) {
        repository.setSourceLanguageCode(sourceCode)
        repository.setTargetLanguageCode(targetCode)
    }
}
