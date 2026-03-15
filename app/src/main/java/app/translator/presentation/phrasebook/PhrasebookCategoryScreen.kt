package app.translator.presentation.phrasebook

import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.widget.Toast
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.translator.core_res.R
import app.translator.core_res.ui.theme.White
import app.translator.domain.model.PhraseCategoryType
import app.translator.domain.model.PhraseItem
import app.translator.presentation.components.AppTopBar
import app.translator.presentation.components.AudioActionButton
import app.translator.presentation.components.AudioActionState
import java.util.Locale

@Composable
fun PhrasebookCategoryScreen(
    viewModel: PhrasebookCategoryViewModel,
    onBack: () -> Unit,
    onSettings: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = androidx.compose.ui.platform.LocalContext.current
    val mainHandler = remember { Handler(Looper.getMainLooper()) }
    var isTtsReady by remember { mutableStateOf(false) }
    val tts = remember(context) {
        TextToSpeech(context) { status ->
            isTtsReady = status == TextToSpeech.SUCCESS
        }
    }
    var audioFinishedSignal by remember { mutableStateOf(0) }

    DisposableEffect(tts) {
        val listener = object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) = Unit

            override fun onDone(utteranceId: String?) {
                mainHandler.post { audioFinishedSignal++ }
            }

            override fun onError(utteranceId: String?) {
                mainHandler.post { audioFinishedSignal++ }
            }
        }
        tts.setOnUtteranceProgressListener(listener)
        onDispose {
            tts.stop()
            tts.shutdown()
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = categoryTitle(uiState.categoryType),
                coinBalance = uiState.coinBalance,
                showBack = true,
                onBackClick = onBack
            ) {
                androidx.compose.material3.IconButton(onClick = onSettings) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = stringResource(R.string.settings)
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = dimensionResource(R.dimen._12dp))
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen._12dp))
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen._4dp)))
            PhraseList(
                phrases = uiState.phrases,
                audioFinishedSignal = audioFinishedSignal,
                onAudioAction = { action, text ->
                    if (isTtsReady) {
                        handleAudioAction(action, text, tts)
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.voice_not_available),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen._12dp)))
        }
    }
}

@Composable
private fun categoryTitle(type: PhraseCategoryType): String {
    val resId = when (type) {
        PhraseCategoryType.Greetings -> R.string.category_greetings
        PhraseCategoryType.EverydayPhrases -> R.string.category_everyday_phrases
        PhraseCategoryType.Transport -> R.string.category_transport
        PhraseCategoryType.Travels -> R.string.category_travels
        PhraseCategoryType.Hotels -> R.string.category_hotels
        PhraseCategoryType.Restaurant -> R.string.category_restaurant
        PhraseCategoryType.Directions -> R.string.category_directions
        PhraseCategoryType.Health -> R.string.category_health
        PhraseCategoryType.Purchases -> R.string.category_purchases
        PhraseCategoryType.Emergency -> R.string.category_emergency
        PhraseCategoryType.Business -> R.string.category_business
        PhraseCategoryType.TimeDate -> R.string.category_time_date
        PhraseCategoryType.Numbers -> R.string.category_numbers
        PhraseCategoryType.Bank -> R.string.category_bank
        PhraseCategoryType.Sports -> R.string.category_sports
        PhraseCategoryType.BeautySalon -> R.string.category_beauty_salon
        PhraseCategoryType.Family -> R.string.category_family
        PhraseCategoryType.Excursions -> R.string.category_excursions
        PhraseCategoryType.FriendlyMeeting -> R.string.category_friendly_meeting
        PhraseCategoryType.PostOffice -> R.string.category_post_office
    }
    return stringResource(resId)
}

private fun speak(tts: TextToSpeech, text: String) {
    tts.language = Locale.getDefault()
    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, text.hashCode().toString())
}

private fun handleAudioAction(action: AudioActionState, text: String, tts: TextToSpeech) {
    if (action == AudioActionState.Playing) {
        speak(tts, text)
    } else {
        tts.stop()
    }
}

@Composable
private fun PhraseList(
    phrases: List<PhraseItem>,
    audioFinishedSignal: Int,
    onAudioAction: (AudioActionState, String) -> Unit
) {
    var currentKey by remember { mutableStateOf<String?>(null) }
    var currentState by remember { mutableStateOf(AudioActionState.Idle) }
    LaunchedEffect(audioFinishedSignal) {
        if (currentState == AudioActionState.Playing) {
            currentState = AudioActionState.Idle
        }
    }
    phrases.forEach { phrase ->
        val state = if (currentKey == phrase.spanish) currentState else AudioActionState.Idle
        PhraseRow(
            phrase = phrase,
            state = state,
            onAudioClick = {
                val nextState = nextAudioState(state)
                currentKey = phrase.spanish
                currentState = nextState
                onAudioAction(nextState, phrase.spanish)
            }
        )
    }
}

@Composable
private fun PhraseRow(
    phrase: PhraseItem,
    state: AudioActionState,
    onAudioClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(dimensionResource(R.dimen._12dp)),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen._2dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen._12dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen._4dp))
            ) {
                Text(text = phrase.spanish, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                Text(
                    text = phrase.english,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            AudioActionButton(
                state = state,
                onClick = onAudioClick
            )
        }
    }
}

private fun nextAudioState(state: AudioActionState): AudioActionState {
    return if (state == AudioActionState.Idle) {
        AudioActionState.Playing
    } else {
        AudioActionState.Idle
    }
}
