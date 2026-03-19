package app.translator.presentation.phrasebook.detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import app.translator.core_res.ui.base.BaseViewModel
import app.translator.domain.model.PhraseCategoryType
import app.translator.domain.usecase.ObserveCoinBalanceUseCase
import app.translator.domain.usecase.GetPhrasebookPhrasesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class PhrasebookCategoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    observeCoinBalanceUseCase: ObserveCoinBalanceUseCase,
    getPhrasebookPhrasesUseCase: GetPhrasebookPhrasesUseCase
) : BaseViewModel() {

    private val categoryType: PhraseCategoryType =
        PhraseCategoryType.valueOf(savedStateHandle.get<String>("type") ?: PhraseCategoryType.Greetings.name)

    private val _uiState = MutableStateFlow(
        PhrasebookCategoryUiState(
            categoryType = categoryType,
            phrases = getPhrasebookPhrasesUseCase(categoryType),
            coinBalance = 0
        )
    )
    val uiState: StateFlow<PhrasebookCategoryUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            observeCoinBalanceUseCase().collect { balance ->
                _uiState.update { it.copy(coinBalance = balance) }
            }
        }
    }
}
