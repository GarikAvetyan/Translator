package app.translator.domain.repository

import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    fun observeCoinBalance(): Flow<Int>
    suspend fun getCoinBalance(): Int
    suspend fun spendCoins(amount: Int): Boolean
}
