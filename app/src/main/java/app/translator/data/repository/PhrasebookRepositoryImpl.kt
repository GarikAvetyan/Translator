package app.translator.data.repository

import app.translator.data.local.PhrasebookLocalDataSource
import app.translator.domain.model.PhraseCategory
import app.translator.domain.model.PhraseCategoryType
import app.translator.domain.model.PhraseItem
import app.translator.domain.repository.PhrasebookRepository
import javax.inject.Inject

class PhrasebookRepositoryImpl @Inject constructor(
    private val localDataSource: PhrasebookLocalDataSource
) : PhrasebookRepository {
    override fun getCategories(): List<PhraseCategory> = localDataSource.getCategories()

    override fun getPhrasesForCategory(type: PhraseCategoryType): List<PhraseItem> {
        return localDataSource.getPhrases(type)
    }
}
