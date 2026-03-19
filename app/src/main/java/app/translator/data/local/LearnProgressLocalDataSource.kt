package app.translator.data.local

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import app.translator.domain.model.LearnModuleType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LearnProgressLocalDataSource @Inject constructor(
    private val dataStore: PreferencesDataStore
) {
    fun observeUnlockedModules(): Flow<Set<LearnModuleType>> {
        return dataStore.dataStore.data.map { prefs ->
            val raw = prefs[UNLOCKED_KEY].orEmpty()
            raw.mapNotNull { name ->
                runCatching { LearnModuleType.valueOf(name) }.getOrNull()
            }.toSet()
        }
    }

    suspend fun unlockModule(type: LearnModuleType) {
        dataStore.dataStore.edit { prefs ->
            val current = prefs[UNLOCKED_KEY].orEmpty().toMutableSet()
            current.add(type.name)
            prefs[UNLOCKED_KEY] = current
        }
    }

    private companion object {
        val UNLOCKED_KEY = stringSetPreferencesKey("learn_unlocked_modules")
    }
}
