package app.translator.presentation.phrasebook

import app.translator.domain.model.PhraseCategory

data class PhrasebookUiState(
    val searchQuery: String = "",
    val categories: List<PhraseCategory> = emptyList(),
    val coinBalance: Int = 0
)
