package app.translator.presentation.settings

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
        SettingsUiState(coinBalance = 0)
    )
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            observeCoinBalanceUseCase().collect { balance ->
                _uiState.update { it.copy(coinBalance = balance) }
            }
        }
    }
}
