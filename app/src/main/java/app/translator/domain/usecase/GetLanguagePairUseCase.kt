package app.translator.domain.usecase

import app.translator.domain.repository.LanguagePreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetLanguagePairUseCase @Inject constructor(
    private val repository: LanguagePreferencesRepository
) {
    operator fun invoke(): Flow<LanguagePair> {
        return repository.sourceLanguageCode
            .combine(repository.targetLanguageCode) { source, target ->
                LanguagePair(source, target)
            }
    }

    data class LanguagePair(
        val sourceCode: String,
        val targetCode: String
    )
}
