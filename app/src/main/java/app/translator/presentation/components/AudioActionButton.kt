package app.translator.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import app.translator.core_res.R
import app.translator.core_res.ui.theme.White

enum class AudioActionState {
    Idle,
    Playing
}

@Composable
fun AudioActionButton(
    state: AudioActionState,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    val icon = when (state) {
        AudioActionState.Idle -> Icons.Default.PlayArrow
        AudioActionState.Playing -> Icons.Default.Pause
    }
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .size(dimensionResource(R.dimen._40dp))
            .background(
                color = if (enabled) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
                shape = CircleShape
            )
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource,
                indication = ripple(
                    bounded = true,
                    color = colorResource(R.color.ripple_white_35)
                ),
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = White
        )
    }
}
