package app.translator.domain.usecase

import app.translator.domain.model.LearnModule
import app.translator.domain.repository.LearnRepository
import javax.inject.Inject

class GetLearnModulesUseCase @Inject constructor(
    private val repository: LearnRepository
) {
    operator fun invoke(): List<LearnModule> = repository.getLearnModules()
}
