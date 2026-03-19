package app.translator.domain.usecase

import app.translator.domain.model.PhraseCategory
import app.translator.domain.repository.PhrasebookRepository
import javax.inject.Inject

class GetPhrasebookCategoriesUseCase @Inject constructor(
    private val repository: PhrasebookRepository
) {
    operator fun invoke(): List<PhraseCategory> = repository.getCategories()
}
