package app.translator.presentation.translator.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.KeyboardVoice
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import app.translator.core_res.R

@Composable
internal fun QuickActionsRow(
    onVoice: () -> Unit,
    onCamera: () -> Unit,
    onPaste: () -> Unit,
    onCopy: (() -> Unit)?,
    onKeyboard: () -> Unit,
    onTranslate: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen._18dp))) {
            ActionIcon(
                icon = Icons.Default.KeyboardVoice,
                label = stringResource(R.string.voice),
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = onVoice
            )
            ActionIcon(
                icon = Icons.Default.CameraAlt,
                label = stringResource(R.string.camera),
                containerColor = colorResource(R.color.camera_pink),
                onClick = onCamera
            )
            ActionIcon(
                icon = Icons.Default.ContentPaste,
                label = stringResource(R.string.paste),
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = onPaste
            )
            if (onCopy != null) {
                ActionIcon(
                    icon = Icons.Default.ContentCopy,
                    label = stringResource(R.string.copy),
                    containerColor = MaterialTheme.colorScheme.primary,
                    onClick = onCopy
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen._12dp))) {
            ActionIcon(
                icon = Icons.Default.Keyboard,
                label = stringResource(R.string.keyboard),
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = onKeyboard
            )
            ActionIcon(
                icon = Icons.AutoMirrored.Filled.ArrowForward,
                label = stringResource(R.string.translate_action),
                containerColor = MaterialTheme.colorScheme.tertiary,
                onClick = onTranslate,
                iconTint = Color.Black
            )
        }
    }
}

@Composable
private fun ActionIcon(
    icon: ImageVector,
    label: String,
    containerColor: Color,
    onClick: () -> Unit,
    iconTint: Color = Color.White
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        val interactionSource = remember { MutableInteractionSource() }
        Box(
            modifier = Modifier
                .size(dimensionResource(R.dimen._48dp))
                .background(containerColor, CircleShape)
                .clickable(
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
                contentDescription = label,
                tint = iconTint
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen._6dp)))
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
