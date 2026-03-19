package app.translator.presentation.settings.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.SlowMotionVideo
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.translator.core_res.R
import app.translator.presentation.common.component.AppTopBar
import app.translator.presentation.common.component.ListItemCard
import app.translator.presentation.settings.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.settings_title),
                coinBalance = uiState.coinBalance,
                showBack = true,
                onBackClick = onBack
            ) {}
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = dimensionResource(R.dimen._12dp))
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen._10dp))
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen._4dp)))
            ListItemCard(
                icon = Icons.Default.Lock,
                title = stringResource(R.string.remove_ads),
                onClick = {},
                trailing = { ChevronIcon() }
            )
            ListItemCard(
                icon = Icons.Default.SlowMotionVideo,
                title = stringResource(R.string.tts_speed),
                subtitle = stringResource(R.string.tts_speed_value),
                onClick = {},
                trailing = { ChevronIcon() }
            )
            ListItemCard(
                icon = Icons.Default.Language,
                title = stringResource(R.string.learn_language_prompt),
                subtitle = getLocaleDisplayName(uiState.currentLocale),
                onClick = { viewModel.showLanguageDialog() },
                trailing = { ChevronIcon() }
            )
            ListItemCard(
                icon = Icons.Default.Feedback,
                title = stringResource(R.string.app_feedback),
                onClick = {},
                trailing = { ChevronIcon() }
            )
            ListItemCard(
                icon = Icons.Default.SupportAgent,
                title = stringResource(R.string.contact_us),
                onClick = {},
                trailing = { ChevronIcon() }
            )
            ListItemCard(
                icon = Icons.AutoMirrored.Filled.Help,
                title = stringResource(R.string.about_us),
                onClick = {},
                trailing = { ChevronIcon() }
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen._12dp)))
        }
    }

    if (uiState.showLanguageDialog) {
        LanguagePickerDialog(
            currentLocale = uiState.currentLocale,
            onLocaleSelected = { viewModel.setLocale(it) },
            onDismiss = { viewModel.dismissLanguageDialog() }
        )
    }
}

@Composable
private fun LanguagePickerDialog(
    currentLocale: String,
    onLocaleSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val languages = listOf(
        "en" to stringResource(R.string.locale_english),
        "es" to stringResource(R.string.locale_spanish)
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.select_language)) },
        text = {
            Column {
                languages.forEach { (code, name) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onLocaleSelected(code) }
                            .padding(vertical = dimensionResource(R.dimen._8dp)),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = code == currentLocale,
                            onClick = { onLocaleSelected(code) }
                        )
                        Text(
                            text = name,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = dimensionResource(R.dimen._8dp))
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.back))
            }
        }
    )
}

@Composable
private fun getLocaleDisplayName(locale: String): String {
    return when (locale) {
        "es" -> stringResource(R.string.locale_spanish)
        else -> stringResource(R.string.locale_english)
    }
}

@Composable
private fun ChevronIcon() {
    Icon(
        imageVector = Icons.Default.ChevronRight,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onSurfaceVariant
    )
}
