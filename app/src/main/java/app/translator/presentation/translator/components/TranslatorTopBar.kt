package app.translator.presentation.translator.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import app.translator.core_res.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TranslatorTopBar(
    coinBalance: Int,
    onSettingsClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.translator_title),
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            CircleIcon(
                icon = Icons.Default.StarBorder,
                contentDescription = stringResource(R.string.favorite),
                containerColor = Color.Transparent,
                iconTint = Color.White,
                iconSize = dimensionResource(R.dimen._22dp)
            )
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen._8dp)))
            Icon(
                imageVector = Icons.Default.History,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(dimensionResource(R.dimen._22dp))
            )
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen._8dp)))
            IconButton(onClick = onSettingsClick) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = stringResource(R.string.settings),
                    modifier = Modifier.size(dimensionResource(R.dimen._22dp))
                )
            }
            CoinsChip(coinBalance = coinBalance)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
private fun CircleIcon(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    containerColor: Color,
    iconTint: Color,
    iconSize: Dp
) {
    Box(
        modifier = Modifier
            .size(dimensionResource(R.dimen._32dp))
            .background(containerColor, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = iconTint,
            modifier = Modifier.size(iconSize)
        )
    }
}

@Composable
private fun CoinsChip(coinBalance: Int) {
    Row(
        modifier = Modifier
            .padding(
                start = dimensionResource(R.dimen._8dp),
                end = dimensionResource(R.dimen._12dp)
            )
            .background(
                MaterialTheme.colorScheme.tertiary,
                RoundedCornerShape(dimensionResource(R.dimen._20dp))
            )
            .padding(
                horizontal = dimensionResource(R.dimen._10dp),
                vertical = dimensionResource(R.dimen._6dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen._6dp))
    ) {
        Icon(
            imageVector = Icons.Default.MonetizationOn,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(dimensionResource(R.dimen._16dp))
        )
        Text(
            text = stringResource(R.string.coins_format, coinBalance),
            color = Color.White,
            fontSize = 12.sp
        )
    }
}
