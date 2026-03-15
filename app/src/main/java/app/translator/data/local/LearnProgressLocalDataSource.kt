package app.translator.data.local

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import app.translator.domain.model.LearnModuleType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class LearnProgressLocalDataSource(
    private val dataStore: PreferencesDataStore
) {
    private val unlockedKey = stringSetPreferencesKey("learn_unlocked_modules")

    fun observeUnlockedModules(): Flow<Set<LearnModuleType>> {
        return dataStore.dataStore.data.map { prefs ->
            val raw = prefs[unlockedKey].orEmpty()
            raw.mapNotNull { name ->
                runCatching { LearnModuleType.valueOf(name) }.getOrNull()
            }.toSet()
        }
    }

    suspend fun unlockModule(type: LearnModuleType) {
        dataStore.dataStore.edit { prefs ->
            val current = prefs[unlockedKey].orEmpty().toMutableSet()
            current.add(type.name)
            prefs[unlockedKey] = current
        }
    }

    suspend fun clearUnlocked() {
        dataStore.dataStore.edit { prefs ->
            prefs.remove(unlockedKey)
        }
    }
}
