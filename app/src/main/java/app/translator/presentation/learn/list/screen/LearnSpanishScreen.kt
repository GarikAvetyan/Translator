package app.translator.presentation.learn.list.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.translator.core_res.R
import app.translator.core_res.ui.theme.White
import app.translator.domain.model.LearnModule
import app.translator.domain.model.LearnModuleType
import app.translator.presentation.common.component.AppTopBar
import app.translator.presentation.translator.component.BottomNavItem
import app.translator.presentation.translator.component.TranslatorBottomNav
import app.translator.presentation.learn.list.viewmodel.LearnSpanishViewModel

@Composable
fun LearnSpanishScreen(
    viewModel: LearnSpanishViewModel,
    onModuleSelected: (LearnModuleType) -> Unit,
    onBack: () -> Unit,
    onSettings: () -> Unit,
    selectedRoute: String,
    onBottomNavSelected: (BottomNavItem) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(uiState.pendingNavigation) {
        val target = uiState.pendingNavigation
        if (target != null) {
            onModuleSelected(target)
            viewModel.onNavigationHandled()
        }
    }

    LaunchedEffect(uiState.messageResId) {
        val resId = uiState.messageResId
        if (resId != null) {
            android.widget.Toast.makeText(
                context,
                context.getString(resId),
                android.widget.Toast.LENGTH_SHORT
            ).show()
            viewModel.clearMessage()
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            AppTopBar(
                title = stringResource(R.string.learn_spanish_title),
                coinBalance = uiState.coinBalance,
                showBack = true,
                onBackClick = onBack
            ) {
                IconButton(onClick = onSettings) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = stringResource(R.string.settings)
                    )
                }
            }
        },
        bottomBar = {
            TranslatorBottomNav(
                selectedRoute = selectedRoute,
                onItemSelected = onBottomNavSelected
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = dimensionResource(R.dimen._12dp))
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen._12dp))
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen._4dp)))
            uiState.modules.forEach { module ->
                val locked = uiState.lockedModules.contains(module.type)
                val cost = uiState.moduleCosts[module.type]
                LearnModuleCard(
                    module = module,
                    isLocked = locked,
                    cost = cost,
                    onClick = { viewModel.onModuleSelected(module.type) }
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen._12dp)))
        }
    }
}

@Composable
private fun LearnModuleCard(
    module: LearnModule,
    isLocked: Boolean,
    cost: Int?,
    onClick: () -> Unit
) {
    val icon = remember(module.type) {
        when (module.type) {
            LearnModuleType.Vocabulary -> Icons.Default.Book
            LearnModuleType.Grammar -> Icons.AutoMirrored.Filled.MenuBook
            LearnModuleType.Exam -> Icons.Default.QuestionAnswer
            LearnModuleType.Listen -> Icons.Default.Headphones
            LearnModuleType.Conversation -> Icons.AutoMirrored.Filled.Chat
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen._64dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(dimensionResource(R.dimen._12dp)),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen._2dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = dimensionResource(R.dimen._16dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.Center) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen._6dp))
                ) {
                    Text(
                        text = stringResource(module.titleResId),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    if (isLocked) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(dimensionResource(R.dimen._16dp))
                        )
                    }
                }
                if (isLocked && cost != null) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen._4dp))
                    ) {
                        Icon(
                            imageVector = Icons.Default.MonetizationOn,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.size(dimensionResource(R.dimen._14dp))
                        )
                        Text(
                            text = cost.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .size(dimensionResource(R.dimen._44dp))
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = stringResource(module.titleResId),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
