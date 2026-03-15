package app.translator.domain.repository

import app.translator.domain.model.LearnModuleType
import kotlinx.coroutines.flow.Flow

interface LearnProgressRepository {
    fun observeUnlockedModules(): Flow<Set<LearnModuleType>>
    suspend fun unlockModule(type: LearnModuleType)
}
