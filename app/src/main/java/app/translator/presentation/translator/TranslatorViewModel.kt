package app.translator.presentation.translator

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import app.translator.core_res.ui.base.BaseViewModel
import app.translator.domain.usecase.ObserveCoinBalanceUseCase
import app.translator.domain.usecase.GetLanguagePairUseCase
import app.translator.domain.usecase.SaveLanguagePairUseCase
import app.translator.domain.usecase.TranslateTextUseCase
import app.translator.presentation.translator.TranslatorUiEvent.*
import app.translator.core_res.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TranslatorViewModel @Inject constructor(
    private val translateTextUseCase: TranslateTextUseCase,
    private val getLanguagePairUseCase: GetLanguagePairUseCase,
    private val saveLanguagePairUseCase: SaveLanguagePairUseCase,
    observeCoinBalanceUseCase: ObserveCoinBalanceUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(TranslatorUiState())
    val uiState: StateFlow<TranslatorUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            observeCoinBalanceUseCase().collect { balance ->
                _uiState.update { it.copy(coinBalance = balance) }
            }
        }
        viewModelScope.launch {
            getLanguagePairUseCase().collect { pair ->
                _uiState.update { state ->
                    state.copy(
                        sourceLanguageCode = pair.sourceCode,
                        targetLanguageCode = pair.targetCode
                    )
                }
            }
        }
    }

    fun onEvent(event: TranslatorUiEvent) {
        when (event) {
            is InputChanged -> updateInput(event.text)
            is SourceLanguageSelected -> updateSourceLanguage(event.code)
            is TargetLanguageSelected -> updateTargetLanguage(event.code)
            SwapLanguages -> swapLanguages()
            Translate -> translate()
            ClearInput -> clearInput()
            ClearError -> clearError()
        }
    }

    private fun updateInput(text: String) {
        _uiState.update { it.copy(inputText = text) }
        if (text.isNotBlank()) {
            clearError()
        }
    }

    private fun updateSourceLanguage(code: String) {
        _uiState.update { state ->
            if (code == state.targetLanguageCode) {
                state.copy(
                    sourceLanguageCode = code,
                    targetLanguageCode = state.sourceLanguageCode
                )
            } else {
                state.copy(sourceLanguageCode = code)
            }
        }
        persistLanguages()
    }

    private fun updateTargetLanguage(code: String) {
        _uiState.update { state ->
            if (code == state.sourceLanguageCode) {
                state.copy(
                    targetLanguageCode = code,
                    sourceLanguageCode = state.targetLanguageCode
                )
            } else {
                state.copy(targetLanguageCode = code)
            }
        }
        persistLanguages()
    }

    private fun swapLanguages() {
        _uiState.update { state ->
            state.copy(
                sourceLanguageCode = state.targetLanguageCode,
                targetLanguageCode = state.sourceLanguageCode
            )
        }
        persistLanguages()
    }

    private fun translate() {
        val state = _uiState.value
        val text = state.inputText.trim()
        if (text.isEmpty()) {
            _uiState.update { it.copy(errorResId = R.string.error_empty_input) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorResId = null) }
            try {
                val result = translateTextUseCase(
                    text = text,
                    source = state.sourceLanguageCode,
                    target = state.targetLanguageCode
                )
                _uiState.update {
                    it.copy(
                        translatedText = result.translatedText,
                        isLoading = false
                    )
                }
            } catch (ex: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorResId = R.string.error_translation_failed
                    )
                }
            }
        }
    }

    private fun clearInput() {
        _uiState.update { it.copy(inputText = "", translatedText = "") }
        clearError()
    }

    private fun clearError() {
        _uiState.update { it.copy(errorResId = null) }
    }

    private fun persistLanguages() {
        val state = _uiState.value
        viewModelScope.launch {
            saveLanguagePairUseCase(
                sourceCode = state.sourceLanguageCode,
                targetCode = state.targetLanguageCode
            )
        }
    }
}

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
