package app.translator.domain.usecase

import app.translator.domain.model.LearnModuleType
import app.translator.domain.repository.LearnProgressRepository
import javax.inject.Inject

class UnlockModuleUseCase @Inject constructor(
    private val repository: LearnProgressRepository
) {
    suspend operator fun invoke(type: LearnModuleType) {
        repository.unlockModule(type)
    }
}
