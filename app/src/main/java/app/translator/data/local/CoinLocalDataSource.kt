package app.translator.data.local

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CoinLocalDataSource @Inject constructor(
    private val dataStore: PreferencesDataStore
) {
    fun observeCoinBalance(): Flow<Int> {
        return dataStore.dataStore.data.map { prefs ->
            prefs[COIN_KEY] ?: DEFAULT_COIN_BALANCE
        }
    }

    suspend fun getCoinBalance(): Int {
        return dataStore.dataStore.data.first()[COIN_KEY] ?: DEFAULT_COIN_BALANCE
    }

    suspend fun spendCoins(amount: Int): Boolean {
        val current = getCoinBalance()
        if (current < amount) return false
        dataStore.dataStore.edit { prefs ->
            prefs[COIN_KEY] = current - amount
        }
        return true
    }

    private companion object {
        val COIN_KEY = intPreferencesKey("coin_balance")
        const val DEFAULT_COIN_BALANCE = 8
    }
}
