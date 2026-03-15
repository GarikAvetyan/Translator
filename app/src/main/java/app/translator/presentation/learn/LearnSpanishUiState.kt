package app.translator.presentation.learn

import app.translator.domain.model.LearnModule
import app.translator.domain.model.LearnModuleType
import androidx.annotation.StringRes

data class LearnSpanishUiState(
    val modules: List<LearnModule> = emptyList(),
    val coinBalance: Int = 0,
    val lockedModules: Set<LearnModuleType> = emptySet(),
    val moduleCosts: Map<LearnModuleType, Int> = emptyMap(),
    val pendingNavigation: LearnModuleType? = null,
    @StringRes val messageResId: Int? = null
)
