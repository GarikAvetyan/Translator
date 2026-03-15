package app.translator.presentation.phrasebook

import app.translator.core_res.ui.base.BaseViewModel
import app.translator.domain.model.PhraseCategory
import app.translator.domain.usecase.ObserveCoinBalanceUseCase
import app.translator.domain.usecase.GetPhrasebookCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class PhrasebookViewModel @Inject constructor(
    getPhrasebookCategoriesUseCase: GetPhrasebookCategoriesUseCase,
    observeCoinBalanceUseCase: ObserveCoinBalanceUseCase
) : BaseViewModel() {

    private val allCategories: List<PhraseCategory> = getPhrasebookCategoriesUseCase()

    private val _uiState = MutableStateFlow(
        PhrasebookUiState(
            categories = allCategories,
            coinBalance = 0
        )
    )
    val uiState: StateFlow<PhrasebookUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            observeCoinBalanceUseCase().collect { balance ->
                _uiState.update { it.copy(coinBalance = balance) }
            }
        }
    }

    fun onSearchChanged(query: String) {
        _uiState.update { state -> state.copy(searchQuery = query) }
    }
}
