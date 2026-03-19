package app.translator.data.repository

import app.translator.data.local.CoinLocalDataSource
import app.translator.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val localDataSource: CoinLocalDataSource
) : CoinRepository {
    override fun observeCoinBalance(): Flow<Int> = localDataSource.observeCoinBalance()

    override suspend fun getCoinBalance(): Int = localDataSource.getCoinBalance()

    override suspend fun spendCoins(amount: Int): Boolean = localDataSource.spendCoins(amount)
}
