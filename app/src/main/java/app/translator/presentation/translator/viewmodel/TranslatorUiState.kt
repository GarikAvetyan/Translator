package app.translator.presentation.translator.viewmodel

import androidx.annotation.StringRes

data class TranslatorUiState(
    val sourceLanguageCode: String = "en",
    val targetLanguageCode: String = "es",
    val inputText: String = "",
    val translatedText: String = "",
    val coinBalance: Int = 0,
    val isLoading: Boolean = false,
    @StringRes val errorResId: Int? = null
)

sealed interface TranslatorUiEvent {
    data class InputChanged(val text: String) : TranslatorUiEvent
    data class SourceLanguageSelected(val code: String) : TranslatorUiEvent
    data class TargetLanguageSelected(val code: String) : TranslatorUiEvent
    data object SwapLanguages : TranslatorUiEvent
    data object Translate : TranslatorUiEvent
    data object ClearInput : TranslatorUiEvent
    data object ClearError : TranslatorUiEvent
}
