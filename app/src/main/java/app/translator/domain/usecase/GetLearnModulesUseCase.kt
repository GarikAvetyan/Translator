package app.translator.domain.usecase

import app.translator.domain.model.LearnModule
import app.translator.domain.repository.LearnRepository

class GetLearnModulesUseCase(
    private val repository: LearnRepository
) {
    operator fun invoke(): List<LearnModule> = repository.getLearnModules()
}
