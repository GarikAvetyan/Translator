package app.translator.presentation.phrasebook

import app.translator.domain.model.PhraseCategoryType
import app.translator.domain.model.PhraseItem

data class PhrasebookCategoryUiState(
    val categoryType: PhraseCategoryType = PhraseCategoryType.Greetings,
    val phrases: List<PhraseItem> = emptyList(),
    val coinBalance: Int = 0
)
