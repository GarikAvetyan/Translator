package app.translator.presentation.translator.component

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.GTranslate
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import app.translator.core_res.R
import app.translator.presentation.navigation.Routes

enum class BottomNavItem(
    val route: String,
    @StringRes val labelResId: Int,
    val icon: ImageVector
) {
    Translator(Routes.Translator, R.string.bottom_nav_translator, Icons.Default.GTranslate),
    Learn(Routes.LearnSpanish, R.string.bottom_nav_learn, Icons.AutoMirrored.Filled.MenuBook),
    Phrasebook(Routes.Phrasebook, R.string.bottom_nav_phrasebook, Icons.Default.Book)
}

@Composable
fun TranslatorBottomNav(
    selectedRoute: String,
    onItemSelected: (BottomNavItem) -> Unit
) {
    Column {
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomNavItem.entries.forEach { item ->
                    val selected = item.route == selectedRoute
                    val color = if (selected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                    Column(
                        modifier = Modifier
                            .padding(vertical = dimensionResource(R.dimen._8dp))
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { onItemSelected(item) },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = stringResource(item.labelResId),
                            tint = color
                        )
                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen._6dp)))
                        Text(
                            text = stringResource(item.labelResId),
                            style = MaterialTheme.typography.labelMedium,
                            color = color
                        )
                    }
                }
            }
        }
    }
}
