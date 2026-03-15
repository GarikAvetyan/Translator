package app.translator.domain.usecase

import app.translator.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow

class ObserveCoinBalanceUseCase(
    private val repository: CoinRepository
) {
    operator fun invoke(): Flow<Int> = repository.observeCoinBalance()
}
