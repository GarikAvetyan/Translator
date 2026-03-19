package app.translator.presentation.translator.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.translator.core_res.R
import app.translator.presentation.translator.viewmodel.TranslatorViewModel
import app.translator.presentation.translator.viewmodel.TranslatorUiState
import app.translator.presentation.translator.viewmodel.TranslatorUiEvent
import app.translator.presentation.translator.component.BottomNavItem
import app.translator.presentation.translator.component.InputCard
import app.translator.presentation.translator.component.LanguageOption
import app.translator.presentation.translator.component.LanguageSwitchRow
import app.translator.presentation.translator.component.OutputCard
import app.translator.presentation.translator.component.QuickActionsRow
import app.translator.presentation.translator.component.TranslatorBottomNav
import app.translator.presentation.translator.component.TranslatorTopBar
import app.translator.presentation.common.component.AudioActionState
import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import android.widget.Toast
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.os.Handler
import android.os.Looper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.DisposableEffect
import java.util.Locale
import kotlinx.coroutines.launch

@Composable
fun TranslatorScreen(
    viewModel: TranslatorViewModel,
    selectedRoute: String,
    onBottomNavSelected: (BottomNavItem) -> Unit,
    onSettingsClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(uiState.errorResId) {
        val errorResId = uiState.errorResId
        if (errorResId != null) {
            snackbarHostState.showSnackbar(
                message = context.getString(errorResId)
            )
            viewModel.onEvent(TranslatorUiEvent.ClearError)
        }
    }

    Scaffold(
        topBar = {
            TranslatorTopBar(
                coinBalance = uiState.coinBalance,
                onFavoritesClick = { },
                onHistoryClick = { },
                onSettingsClick = onSettingsClick
            )
        },
        bottomBar = {
            TranslatorBottomNav(
                selectedRoute = selectedRoute,
                onItemSelected = onBottomNavSelected
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        TranslatorContent(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            uiState = uiState,
            onEvent = viewModel::onEvent,
            onQuickActionClick = {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = context.getString(R.string.feature_not_available)
                    )
                }
            }
        )
    }
}

@Composable
private fun TranslatorContent(
    modifier: Modifier,
    uiState: TranslatorUiState,
    onEvent: (TranslatorUiEvent) -> Unit,
    onQuickActionClick: () -> Unit
) {
    val languageOptions = remember {
        listOf(
            LanguageOption("en", R.string.language_english),
            LanguageOption("es", R.string.language_spanish)
        )
    }
    val clipboardManager = LocalClipboardManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val inputFocusRequester = remember { FocusRequester() }
    val context = LocalContext.current
    val mainHandler = remember { Handler(Looper.getMainLooper()) }
    var isTtsReady by remember { mutableStateOf(false) }
    val tts = remember(context) {
        TextToSpeech(context) { status ->
            isTtsReady = status == TextToSpeech.SUCCESS
        }
    }
    var outputAudioState by remember { mutableStateOf(AudioActionState.Idle) }

    DisposableEffect(tts) {
        val listener = object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) = Unit

            override fun onDone(utteranceId: String?) {
                mainHandler.post { outputAudioState = AudioActionState.Idle }
            }

            override fun onError(utteranceId: String?) {
                mainHandler.post { outputAudioState = AudioActionState.Idle }
            }
        }
        tts.setOnUtteranceProgressListener(listener)
        onDispose {
            tts.stop()
            tts.shutdown()
        }
    }

    LaunchedEffect(uiState.translatedText) {
        outputAudioState = AudioActionState.Idle
    }

    val speechLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val spokenText = result.data
                ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                ?.firstOrNull()
                ?.trim()
            if (!spokenText.isNullOrEmpty()) {
                onEvent(TranslatorUiEvent.InputChanged(spokenText))
                inputFocusRequester.requestFocus()
                keyboardController?.show()
            } else {
                Toast.makeText(
                    context,
                    context.getString(R.string.voice_no_match),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            launchSpeechRecognizer(context, speechLauncher::launch)
        } else {
            Toast.makeText(
                context,
                context.getString(R.string.voice_permission_denied),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Column(
        modifier = modifier
            .padding(horizontal = dimensionResource(R.dimen._4dp))
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen._16dp))
    ) {
        LanguageSwitchRow(
            languageOptions = languageOptions,
            sourceCode = uiState.sourceLanguageCode,
            targetCode = uiState.targetLanguageCode,
            onSourceSelected = { onEvent(TranslatorUiEvent.SourceLanguageSelected(it)) },
            onTargetSelected = { onEvent(TranslatorUiEvent.TargetLanguageSelected(it)) },
            onSwap = { onEvent(TranslatorUiEvent.SwapLanguages) }
        )

        InputCard(
            text = uiState.inputText,
            isLoading = uiState.isLoading,
            onTextChange = { onEvent(TranslatorUiEvent.InputChanged(it)) },
            onClear = { onEvent(TranslatorUiEvent.ClearInput) },
            focusRequester = inputFocusRequester
        )

        QuickActionsRow(
            onVoice = {
                permissionLauncher.launch(android.Manifest.permission.RECORD_AUDIO)
            },
            onCamera = onQuickActionClick,
            onPaste = {
                val pasted = clipboardManager.getText()?.text?.trim()
                if (!pasted.isNullOrEmpty()) {
                    onEvent(TranslatorUiEvent.InputChanged(pasted))
                    inputFocusRequester.requestFocus()
                    keyboardController?.show()
                }
            },
            onCopy = uiState.inputText.takeIf { it.isNotBlank() }?.let {
                {
                    clipboardManager.setText(AnnotatedString(it))
                }
            },
            onKeyboard = {
                inputFocusRequester.requestFocus()
                keyboardController?.show()
            },
            onTranslate = { onEvent(TranslatorUiEvent.Translate) }
        )

        OutputCard(
            text = uiState.translatedText,
            isLoading = uiState.isLoading,
            onCopy = uiState.translatedText.takeIf { it.isNotBlank() }?.let {
                {
                    clipboardManager.setText(AnnotatedString(it))
                }
            },
            onShare = uiState.translatedText.takeIf { it.isNotBlank() }?.let { text ->
                {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, text)
                    }
                    context.startActivity(Intent.createChooser(intent, null))
                }
            },
            onFavorite = null,
            audioState = uiState.translatedText.takeIf { it.isNotBlank() }?.let { outputAudioState },
            onAudioClick = uiState.translatedText.takeIf { it.isNotBlank() }?.let { text ->
                if (!isTtsReady) {
                    {
                        Toast.makeText(
                            context,
                            context.getString(R.string.voice_not_available),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    {
                        outputAudioState = nextAudioState(outputAudioState)
                        if (outputAudioState == AudioActionState.Playing) {
                            tts.language = Locale.getDefault()
                            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "translation")
                        } else {
                            tts.stop()
                        }
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen._12dp)))
    }
}

private fun nextAudioState(state: AudioActionState): AudioActionState {
    return if (state == AudioActionState.Idle) {
        AudioActionState.Playing
    } else {
        AudioActionState.Idle
    }
}

private fun launchSpeechRecognizer(
    context: android.content.Context,
    launch: (Intent) -> Unit
) {
    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        putExtra(RecognizerIntent.EXTRA_PROMPT, context.getString(R.string.voice_prompt))
    }
    if (intent.resolveActivity(context.packageManager) != null) {
        launch(intent)
    } else {
        Toast.makeText(
            context,
            context.getString(R.string.voice_not_available),
            Toast.LENGTH_SHORT
        ).show()
    }
}
