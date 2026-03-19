package app.translator.presentation.phrasebook.detail.screen

import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.widget.Toast
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.translator.core_res.R
import app.translator.domain.model.PhraseCategoryType
import app.translator.domain.model.PhraseItem
import app.translator.presentation.common.component.AppTopBar
import app.translator.presentation.common.component.AudioActionState
import app.translator.presentation.common.component.PhraseRowCard
import app.translator.presentation.phrasebook.detail.viewmodel.PhrasebookCategoryViewModel
import java.util.Locale

@Composable
fun PhrasebookCategoryScreen(
    viewModel: PhrasebookCategoryViewModel,
    onBack: () -> Unit,
    onSettings: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
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
                title = stringResource(categoryTitleRes(uiState.categoryType)),
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
                        if (action == AudioActionState.Playing) {
                            tts.language = Locale("es")
                            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, text.hashCode().toString())
                        } else {
                            tts.stop()
                        }
                    } else {
                        Toast.makeText(context, context.getString(R.string.voice_not_available), Toast.LENGTH_SHORT).show()
                    }
                }
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen._12dp)))
        }
    }
}

private fun categoryTitleRes(type: PhraseCategoryType): Int {
    return when (type) {
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
}

@Composable
private fun PhraseList(
    phrases: List<PhraseItem>,
    audioFinishedSignal: Int,
    onAudioAction: (AudioActionState, String) -> Unit
) {
    var currentKey by remember { mutableStateOf<Int?>(null) }
    var currentState by remember { mutableStateOf(AudioActionState.Idle) }
    LaunchedEffect(audioFinishedSignal) {
        if (currentState == AudioActionState.Playing) {
            currentState = AudioActionState.Idle
        }
    }
    phrases.forEach { phrase ->
        val spanish = stringResource(phrase.spanishResId)
        val english = stringResource(phrase.englishResId)
        val state = if (currentKey == phrase.spanishResId) currentState else AudioActionState.Idle
        PhraseRowCard(
            title = spanish,
            subtitle = english,
            audioState = state,
            onAudioClick = {
                val nextState = if (state == AudioActionState.Idle) AudioActionState.Playing else AudioActionState.Idle
                currentKey = phrase.spanishResId
                currentState = nextState
                onAudioAction(nextState, spanish)
            }
        )
    }
}
