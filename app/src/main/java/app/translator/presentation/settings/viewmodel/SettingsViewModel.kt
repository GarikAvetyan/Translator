package app.translator.presentation.settings.viewmodel

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import app.translator.core_res.ui.base.BaseViewModel
import app.translator.domain.usecase.ObserveCoinBalanceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(
    observeCoinBalanceUseCase: ObserveCoinBalanceUseCase
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(
        SettingsUiState(
            coinBalance = 0,
            currentLocale = getCurrentLocale()
        )
    )
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            observeCoinBalanceUseCase().collect { balance ->
                _uiState.update { it.copy(coinBalance = balance) }
            }
        }
    }

    fun showLanguageDialog() {
        _uiState.update { it.copy(showLanguageDialog = true) }
    }

    fun dismissLanguageDialog() {
        _uiState.update { it.copy(showLanguageDialog = false) }
    }

    fun setLocale(languageTag: String) {
        val localeList = LocaleListCompat.forLanguageTags(languageTag)
        AppCompatDelegate.setApplicationLocales(localeList)
        _uiState.update {
            it.copy(currentLocale = languageTag, showLanguageDialog = false)
        }
    }

    private fun getCurrentLocale(): String {
        val locales = AppCompatDelegate.getApplicationLocales()
        return if (locales.isEmpty) "en" else locales[0]?.language ?: "en"
    }
}
