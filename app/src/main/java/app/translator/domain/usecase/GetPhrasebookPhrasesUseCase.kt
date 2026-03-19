package app.translator.domain.usecase

import app.translator.domain.model.PhraseCategoryType
import app.translator.domain.model.PhraseItem
import app.translator.domain.repository.PhrasebookRepository
import javax.inject.Inject

class GetPhrasebookPhrasesUseCase @Inject constructor(
    private val repository: PhrasebookRepository
) {
    operator fun invoke(type: PhraseCategoryType): List<PhraseItem> {
        return repository.getPhrasesForCategory(type)
    }
}
