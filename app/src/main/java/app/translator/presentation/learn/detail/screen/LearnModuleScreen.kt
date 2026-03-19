package app.translator.presentation.learn.detail.screen

import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.widget.Toast
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.translator.core_res.R
import app.translator.core_res.ui.theme.White
import app.translator.domain.model.ConversationPhrase
import app.translator.domain.model.ExamQuestion
import app.translator.domain.model.GrammarLesson
import app.translator.domain.model.LearnModuleType
import app.translator.domain.model.ListenItem
import app.translator.domain.model.VocabularyWord
import app.translator.presentation.common.component.AppTopBar
import app.translator.presentation.common.component.AudioActionState
import app.translator.presentation.common.component.PhraseRowCard
import app.translator.presentation.learn.detail.viewmodel.LearnModuleViewModel
import app.translator.presentation.learn.detail.viewmodel.LearnModuleUiState
import java.util.Locale

@Composable
fun LearnModuleScreen(
    viewModel: LearnModuleViewModel,
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

    val onAudioAction: (AudioActionState, String) -> Unit = { action, text ->
        if (isTtsReady) {
            handleAudioAction(action, text, tts)
        } else {
            Toast.makeText(context, context.getString(R.string.voice_not_available), Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(moduleTitleRes(uiState.moduleType)),
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
            when (uiState.moduleType) {
                LearnModuleType.Vocabulary -> VocabularySection(
                    words = uiState.vocabulary,
                    audioFinishedSignal = audioFinishedSignal,
                    onAudioAction = onAudioAction
                )
                LearnModuleType.Grammar -> GrammarSection(uiState.grammarLessons)
                LearnModuleType.Exam -> ExamSection(
                    uiState = uiState,
                    onOptionSelected = viewModel::onExamOptionSelected,
                    onNext = viewModel::onExamNext,
                    onRestart = viewModel::onExamRestart
                )
                LearnModuleType.Listen -> ListenSection(
                    items = uiState.listenItems,
                    audioFinishedSignal = audioFinishedSignal,
                    onAudioAction = onAudioAction
                )
                LearnModuleType.Conversation -> ConversationSection(
                    items = uiState.conversationPhrases,
                    audioFinishedSignal = audioFinishedSignal,
                    onAudioAction = onAudioAction
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen._12dp)))
        }
    }
}

private fun moduleTitleRes(type: LearnModuleType): Int {
    return when (type) {
        LearnModuleType.Vocabulary -> R.string.learn_vocab
        LearnModuleType.Grammar -> R.string.learn_grammar
        LearnModuleType.Exam -> R.string.learn_exam
        LearnModuleType.Listen -> R.string.learn_listen
        LearnModuleType.Conversation -> R.string.learn_conversation
    }
}

private fun handleAudioAction(action: AudioActionState, text: String, tts: TextToSpeech) {
    if (action == AudioActionState.Playing) {
        tts.language = Locale("es")
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, text.hashCode().toString())
    } else {
        tts.stop()
    }
}

private fun nextAudioState(state: AudioActionState): AudioActionState {
    return if (state == AudioActionState.Idle) AudioActionState.Playing else AudioActionState.Idle
}

@Composable
private fun VocabularySection(
    words: List<VocabularyWord>,
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
    words.forEach { word ->
        val spanish = stringResource(word.spanishResId)
        val english = stringResource(word.englishResId)
        val state = if (currentKey == word.spanishResId) currentState else AudioActionState.Idle
        PhraseRowCard(
            title = spanish,
            subtitle = english,
            audioState = state,
            onAudioClick = {
                val nextState = nextAudioState(state)
                currentKey = word.spanishResId
                currentState = nextState
                onAudioAction(nextState, spanish)
            }
        )
    }
}

@Composable
private fun GrammarSection(lessons: List<GrammarLesson>) {
    lessons.forEach { lesson ->
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(dimensionResource(R.dimen._12dp)),
            colors = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen._2dp))
        ) {
            Column(
                modifier = Modifier.padding(dimensionResource(R.dimen._12dp)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen._6dp))
            ) {
                Text(
                    text = stringResource(lesson.titleResId),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = stringResource(lesson.explanationResId),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = stringResource(lesson.exampleResId),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
private fun ExamSection(
    uiState: LearnModuleUiState,
    onOptionSelected: (Int) -> Unit,
    onNext: () -> Unit,
    onRestart: () -> Unit
) {
    if (uiState.examQuestions.isEmpty()) {
        Text(text = stringResource(R.string.exam_no_questions))
        return
    }

    if (uiState.examFinished) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(dimensionResource(R.dimen._12dp)),
            colors = CardDefaults.cardColors(containerColor = White)
        ) {
            Column(
                modifier = Modifier.padding(dimensionResource(R.dimen._16dp)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen._8dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.exam_score),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${uiState.examScore}/${uiState.examQuestions.size}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                TextButton(onClick = onRestart) {
                    Text(text = stringResource(R.string.exam_try_again))
                }
            }
        }
        return
    }

    val question = uiState.examQuestions[uiState.examIndex]
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(R.dimen._12dp)),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen._16dp)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen._8dp))
        ) {
            Text(
                text = stringResource(R.string.exam_question, uiState.examIndex + 1),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = stringResource(question.questionResId),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            question.optionResIds.forEachIndexed { index, optionResId ->
                val selected = uiState.selectedOptionIndex == index
                val background = if (selected) {
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                } else {
                    Color.Transparent
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(background, RoundedCornerShape(dimensionResource(R.dimen._8dp)))
                        .clickable { onOptionSelected(index) }
                        .padding(dimensionResource(R.dimen._10dp)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(optionResId),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            TextButton(
                onClick = onNext,
                enabled = uiState.selectedOptionIndex != null,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = if (uiState.examIndex == uiState.examQuestions.lastIndex) {
                        stringResource(R.string.exam_finish)
                    } else {
                        stringResource(R.string.exam_next)
                    }
                )
            }
        }
    }
}

@Composable
private fun ListenSection(
    items: List<ListenItem>,
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
    items.forEach { item ->
        val spanish = stringResource(item.spanishResId)
        val english = stringResource(item.englishResId)
        val state = if (currentKey == item.spanishResId) currentState else AudioActionState.Idle
        PhraseRowCard(
            title = spanish,
            subtitle = english,
            audioState = state,
            onAudioClick = {
                val nextState = nextAudioState(state)
                currentKey = item.spanishResId
                currentState = nextState
                onAudioAction(nextState, spanish)
            }
        )
    }
}

@Composable
private fun ConversationSection(
    items: List<ConversationPhrase>,
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
    items.forEach { item ->
        val spanish = stringResource(item.spanishResId)
        val english = stringResource(item.englishResId)
        val state = if (currentKey == item.spanishResId) currentState else AudioActionState.Idle
        PhraseRowCard(
            title = spanish,
            subtitle = english,
            audioState = state,
            onAudioClick = {
                val nextState = nextAudioState(state)
                currentKey = item.spanishResId
                currentState = nextState
                onAudioAction(nextState, spanish)
            }
        )
    }
}
