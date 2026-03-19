package app.translator.domain.model

sealed interface TranslationResult {
    data class Success(val translatedText: String) : TranslationResult
    data class Error(val exception: Throwable) : TranslationResult
}
