package app.translator.presentation.settings.viewmodel

data class SettingsUiState(
    val coinBalance: Int = 0,
    val currentLocale: String = "en",
    val showLanguageDialog: Boolean = false
)
