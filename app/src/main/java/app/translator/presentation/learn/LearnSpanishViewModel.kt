package app.translator.presentation.learn

import app.translator.core_res.ui.base.BaseViewModel
import app.translator.domain.usecase.ObserveCoinBalanceUseCase
import app.translator.domain.usecase.ObserveUnlockedModulesUseCase
import app.translator.domain.usecase.SpendCoinsUseCase
import app.translator.domain.usecase.UnlockModuleUseCase
import app.translator.domain.usecase.GetLearnModulesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

@HiltViewModel
class LearnSpanishViewModel @Inject constructor(
    getLearnModulesUseCase: GetLearnModulesUseCase,
    observeCoinBalanceUseCase: ObserveCoinBalanceUseCase,
    observeUnlockedModulesUseCase: ObserveUnlockedModulesUseCase,
    private val spendCoinsUseCase: SpendCoinsUseCase,
    private val unlockModuleUseCase: UnlockModuleUseCase
) : BaseViewModel() {

    private val moduleCosts = mapOf(
        app.translator.domain.model.LearnModuleType.Exam to 3,
        app.translator.domain.model.LearnModuleType.Listen to 2,
        app.translator.domain.model.LearnModuleType.Conversation to 2
    )

    private val baseLockedModules = setOf(
        app.translator.domain.model.LearnModuleType.Exam,
        app.translator.domain.model.LearnModuleType.Listen,
        app.translator.domain.model.LearnModuleType.Conversation
    )

    private val _uiState = MutableStateFlow(
        LearnSpanishUiState(
            modules = getLearnModulesUseCase(),
            coinBalance = 0,
            lockedModules = baseLockedModules,
            moduleCosts = moduleCosts
        )
    )
    val uiState: StateFlow<LearnSpanishUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            observeCoinBalanceUseCase().collect { balance ->
                _uiState.update { it.copy(coinBalance = balance) }
            }
        }
        viewModelScope.launch {
            observeUnlockedModulesUseCase().collect { unlocked ->
                val locked = baseLockedModules.minus(unlocked)
                _uiState.update { it.copy(lockedModules = locked) }
            }
        }
    }

    fun onModuleSelected(type: app.translator.domain.model.LearnModuleType) {
        val isLocked = _uiState.value.lockedModules.contains(type)
        if (!isLocked) {
            _uiState.update { it.copy(pendingNavigation = type, messageResId = null) }
            return
        }
        val cost = moduleCosts[type] ?: 0
        viewModelScope.launch {
            val success = cost <= 0 || spendCoinsUseCase(cost)
            if (success) {
                unlockModuleUseCase(type)
                _uiState.update { it.copy(pendingNavigation = type, messageResId = null) }
            } else {
                _uiState.update { it.copy(messageResId = app.translator.core_res.R.string.not_enough_coins) }
            }
        }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(pendingNavigation = null) }
    }

    fun clearMessage() {
        _uiState.update { it.copy(messageResId = null) }
    }
}
