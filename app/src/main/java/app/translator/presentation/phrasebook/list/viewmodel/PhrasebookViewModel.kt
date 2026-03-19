package app.translator.presentation.phrasebook.list.viewmodel

import android.content.Context
import app.translator.core_res.ui.base.BaseViewModel
import app.translator.domain.model.PhraseCategory
import app.translator.domain.usecase.GetPhrasebookCategoriesUseCase
import app.translator.domain.usecase.ObserveCoinBalanceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class PhrasebookViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    getPhrasebookCategoriesUseCase: GetPhrasebookCategoriesUseCase,
    observeCoinBalanceUseCase: ObserveCoinBalanceUseCase
) : BaseViewModel() {

    private val allCategories: List<PhraseCategory> = getPhrasebookCategoriesUseCase()

    private val _uiState = MutableStateFlow(
        PhrasebookUiState(
            categories = allCategories,
            filteredCategories = allCategories,
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
        _uiState.update { state ->
            val filtered = if (query.isBlank()) {
                allCategories
            } else {
                allCategories.filter { category ->
                    val title = context.getString(category.titleResId)
                    title.contains(query.trim(), ignoreCase = true)
                }
            }
            state.copy(searchQuery = query, filteredCategories = filtered)
        }
    }
}
