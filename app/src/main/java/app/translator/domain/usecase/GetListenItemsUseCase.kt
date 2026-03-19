package app.translator.domain.usecase

import app.translator.domain.model.ListenItem
import app.translator.domain.repository.LearnRepository
import javax.inject.Inject

class GetListenItemsUseCase @Inject constructor(
    private val repository: LearnRepository
) {
    operator fun invoke(): List<ListenItem> = repository.getListenItems()
}
