package app.translator.domain.usecase

import app.translator.domain.model.LearnModuleType
import app.translator.domain.repository.LearnProgressRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveUnlockedModulesUseCase @Inject constructor(
    private val repository: LearnProgressRepository
) {
    operator fun invoke(): Flow<Set<LearnModuleType>> = repository.observeUnlockedModules()
}
