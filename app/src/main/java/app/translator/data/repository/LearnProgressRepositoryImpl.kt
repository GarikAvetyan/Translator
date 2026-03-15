package app.translator.data.repository

import app.translator.data.local.LearnProgressLocalDataSource
import app.translator.domain.model.LearnModuleType
import app.translator.domain.repository.LearnProgressRepository
import kotlinx.coroutines.flow.Flow

class LearnProgressRepositoryImpl(
    private val localDataSource: LearnProgressLocalDataSource
) : LearnProgressRepository {
    override fun observeUnlockedModules(): Flow<Set<LearnModuleType>> =
        localDataSource.observeUnlockedModules()

    override suspend fun unlockModule(type: LearnModuleType) {
        localDataSource.unlockModule(type)
    }
}
