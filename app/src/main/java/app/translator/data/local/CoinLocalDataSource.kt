package app.translator.data.local

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class CoinLocalDataSource(
    private val dataStore: PreferencesDataStore
) {
    private val coinKey = intPreferencesKey("coin_balance")

    fun observeCoinBalance(): Flow<Int> {
        return dataStore.dataStore.data.map { prefs ->
            prefs[coinKey] ?: 8
        }
    }

    suspend fun getCoinBalance(): Int {
        return dataStore.dataStore.data.first()[coinKey] ?: 8
    }

    suspend fun spendCoins(amount: Int): Boolean {
        val current = getCoinBalance()
        if (current < amount) return false
        dataStore.dataStore.edit { prefs ->
            prefs[coinKey] = current - amount
        }
        return true
    }
}
