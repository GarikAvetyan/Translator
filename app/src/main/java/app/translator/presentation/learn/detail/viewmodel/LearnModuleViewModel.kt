package app.translator.presentation.learn.detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import app.translator.core_res.ui.base.BaseViewModel
import app.translator.domain.model.LearnModuleType
import app.translator.domain.usecase.ObserveCoinBalanceUseCase
import app.translator.domain.usecase.GetConversationPhrasesUseCase
import app.translator.domain.usecase.GetExamQuestionsUseCase
import app.translator.domain.usecase.GetGrammarLessonsUseCase
import app.translator.domain.usecase.GetListenItemsUseCase
import app.translator.domain.usecase.GetVocabularyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LearnModuleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    observeCoinBalanceUseCase: ObserveCoinBalanceUseCase,
    getVocabularyUseCase: GetVocabularyUseCase,
    getGrammarLessonsUseCase: GetGrammarLessonsUseCase,
    getExamQuestionsUseCase: GetExamQuestionsUseCase,
    getListenItemsUseCase: GetListenItemsUseCase,
    getConversationPhrasesUseCase: GetConversationPhrasesUseCase
) : BaseViewModel() {

    private val moduleType: LearnModuleType =
        LearnModuleType.valueOf(savedStateHandle.get<String>("type") ?: LearnModuleType.Vocabulary.name)

    private val _uiState = MutableStateFlow(
        LearnModuleUiState(
            moduleType = moduleType,
            vocabulary = getVocabularyUseCase(),
            grammarLessons = getGrammarLessonsUseCase(),
            examQuestions = getExamQuestionsUseCase(),
            listenItems = getListenItemsUseCase(),
            conversationPhrases = getConversationPhrasesUseCase(),
            coinBalance = 0
        )
    )
    val uiState: StateFlow<LearnModuleUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            observeCoinBalanceUseCase().collect { balance ->
                _uiState.update { it.copy(coinBalance = balance) }
            }
        }
    }

    fun onExamOptionSelected(index: Int) {
        _uiState.update { state ->
            if (state.examFinished) state else state.copy(selectedOptionIndex = index)
        }
    }

    fun onExamNext() {
        _uiState.update { state ->
            if (state.examFinished || state.selectedOptionIndex == null) {
                return@update state
            }
            val question = state.examQuestions.getOrNull(state.examIndex) ?: return@update state
            val isCorrect = state.selectedOptionIndex == question.correctIndex
            val nextIndex = state.examIndex + 1
            val nextScore = if (isCorrect) state.examScore + 1 else state.examScore
            if (nextIndex >= state.examQuestions.size) {
                state.copy(
                    examScore = nextScore,
                    examFinished = true,
                    selectedOptionIndex = null
                )
            } else {
                state.copy(
                    examIndex = nextIndex,
                    examScore = nextScore,
                    selectedOptionIndex = null
                )
            }
        }
    }

    fun onExamRestart() {
        _uiState.update { state ->
            state.copy(
                examIndex = 0,
                examScore = 0,
                examFinished = false,
                selectedOptionIndex = null
            )
        }
    }
}
