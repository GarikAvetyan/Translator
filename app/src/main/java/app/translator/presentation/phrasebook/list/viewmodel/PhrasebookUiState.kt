package app.translator.presentation.phrasebook.list.viewmodel

import app.translator.domain.model.PhraseCategory

data class PhrasebookUiState(
    val searchQuery: String = "",
    val categories: List<PhraseCategory> = emptyList(),
    val filteredCategories: List<PhraseCategory> = emptyList(),
    val coinBalance: Int = 0
)
