package app.translator.domain.usecase

import app.translator.domain.model.PhraseCategory
import app.translator.domain.repository.PhrasebookRepository

class GetPhrasebookCategoriesUseCase(
    private val repository: PhrasebookRepository
) {
    operator fun invoke(): List<PhraseCategory> = repository.getCategories()
}
