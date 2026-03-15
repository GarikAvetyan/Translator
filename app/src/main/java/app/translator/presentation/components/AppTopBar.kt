package app.translator.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MonetizationOn
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
import androidx.compose.ui.unit.sp
import app.translator.core_res.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    coinBalance: Int,
    showBack: Boolean,
    onBackClick: () -> Unit,
    actions: @Composable () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            if (showBack) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                        tint = Color.White
                    )
                }
            }
        },
        actions = {
            actions()
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
fun CoinsChip(coinBalance: Int) {
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
        verticalAlignment = Alignment.CenterVertically
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
            fontSize = 12.sp,
            modifier = Modifier.padding(start = dimensionResource(R.dimen._6dp))
        )
    }
}
