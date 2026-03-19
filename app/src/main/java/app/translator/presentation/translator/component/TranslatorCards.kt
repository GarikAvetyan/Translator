package app.translator.presentation.translator.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import app.translator.core_res.R
import app.translator.core_res.ui.theme.White
import app.translator.presentation.common.component.AudioActionState

@Composable
internal fun InputCard(
    text: String,
    isLoading: Boolean,
    onTextChange: (String) -> Unit,
    onClear: () -> Unit,
    focusRequester: FocusRequester
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(R.dimen._16dp)),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(
            modifier = Modifier.padding(all = dimensionResource(R.dimen._4dp)),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                val hasText = text.isNotEmpty()
                IconButton(
                    onClick = onClear,
                    enabled = hasText,
                    modifier = Modifier
                        .size(dimensionResource(R.dimen._32dp))
                        .alpha(if (hasText) 1f else 0f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.clear_action),
                        modifier = Modifier.size(dimensionResource(R.dimen._22dp))
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen._200dp))
            ) {
                TextField(
                    value = text,
                    onValueChange = onTextChange,
                    modifier = Modifier
                        .fillMaxSize()
                        .focusRequester(focusRequester)
                        .verticalScroll(rememberScrollState()),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.input_hint),
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    textStyle = MaterialTheme.typography.headlineMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    maxLines = Int.MAX_VALUE,
                    enabled = true,
                    readOnly = isLoading
                )
            }
        }
    }
}

@Composable
internal fun OutputCard(
    text: String,
    isLoading: Boolean,
    onCopy: (() -> Unit)?,
    onShare: (() -> Unit)?,
    onFavorite: (() -> Unit)?,
    audioState: AudioActionState?,
    onAudioClick: (() -> Unit)?
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(R.dimen._16dp)),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen._4dp))
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = dimensionResource(R.dimen._8dp),
                vertical = dimensionResource(R.dimen._16dp)
            ),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen._12dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen._140dp)),
                contentAlignment = Alignment.TopStart
            ) {
                val outputText = text.ifBlank {
                    stringResource(R.string.output_placeholder)
                }
                Text(
                    text = outputText,
                    style = MaterialTheme.typography.headlineMedium,
                    color = if (text.isNotBlank()) {
                        MaterialTheme.colorScheme.onSurface
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
                    modifier = Modifier.verticalScroll(rememberScrollState())
                )
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(dimensionResource(R.dimen._24dp)),
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = dimensionResource(R.dimen._2dp)
                    )
                }
            }
            val hasText = text.isNotBlank()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(if (hasText) 1f else 0f),
                horizontalArrangement = Arrangement.spacedBy(
                    dimensionResource(R.dimen._12dp),
                    Alignment.End
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (hasText) {
                    OutputActionButton(
                        icon = Icons.Default.StarBorder,
                        label = stringResource(R.string.favorite),
                        enabled = onFavorite != null,
                        onClick = { onFavorite?.invoke() }
                    )
                }
                if (hasText && audioState != null && onAudioClick != null) {
                    OutputActionButton(
                        icon = Icons.AutoMirrored.Filled.VolumeUp,
                        label = stringResource(R.string.play),
                        enabled = true,
                        onClick = { onAudioClick.invoke() }
                    )
                }
                if (hasText && onShare != null) {
                    OutputActionButton(
                        icon = Icons.Default.Share,
                        label = stringResource(R.string.share),
                        enabled = true,
                        onClick = { onShare.invoke() }
                    )
                }
                OutputActionButton(
                    icon = Icons.Default.ContentCopy,
                    label = stringResource(R.string.copy),
                    enabled = hasText && onCopy != null,
                    onClick = { onCopy?.invoke() }
                )
            }
        }
    }
}

@Composable
private fun OutputActionButton(
    icon: ImageVector,
    label: String,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(dimensionResource(R.dimen._48dp))
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.tertiary, CircleShape)
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
                contentDescription = label,
                tint = Color.Black
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
