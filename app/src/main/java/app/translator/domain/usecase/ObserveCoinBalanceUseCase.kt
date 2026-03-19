package app.translator.domain.usecase

import app.translator.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveCoinBalanceUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(): Flow<Int> = repository.observeCoinBalance()
}
