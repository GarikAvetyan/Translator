package app.translator.domain.repository

import app.translator.domain.model.PhraseCategory
import app.translator.domain.model.PhraseCategoryType
import app.translator.domain.model.PhraseItem

interface PhrasebookRepository {
    fun getCategories(): List<PhraseCategory>
    fun getPhrasesForCategory(type: PhraseCategoryType): List<PhraseItem>
}
