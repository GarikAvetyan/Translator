package app.translator.domain.usecase

import app.translator.domain.repository.CoinRepository
import javax.inject.Inject

class SpendCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    suspend operator fun invoke(amount: Int): Boolean = repository.spendCoins(amount)
}
