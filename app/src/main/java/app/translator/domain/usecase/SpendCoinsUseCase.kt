package app.translator.domain.usecase

import app.translator.domain.repository.CoinRepository

class SpendCoinsUseCase(
    private val repository: CoinRepository
) {
    suspend operator fun invoke(amount: Int): Boolean = repository.spendCoins(amount)
}
