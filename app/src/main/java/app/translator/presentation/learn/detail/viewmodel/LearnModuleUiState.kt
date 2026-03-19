package app.translator.presentation.learn.detail.viewmodel

import app.translator.domain.model.ConversationPhrase
import app.translator.domain.model.ExamQuestion
import app.translator.domain.model.GrammarLesson
import app.translator.domain.model.LearnModuleType
import app.translator.domain.model.ListenItem
import app.translator.domain.model.VocabularyWord

data class LearnModuleUiState(
    val moduleType: LearnModuleType = LearnModuleType.Vocabulary,
    val vocabulary: List<VocabularyWord> = emptyList(),
    val grammarLessons: List<GrammarLesson> = emptyList(),
    val examQuestions: List<ExamQuestion> = emptyList(),
    val listenItems: List<ListenItem> = emptyList(),
    val conversationPhrases: List<ConversationPhrase> = emptyList(),
    val examIndex: Int = 0,
    val examScore: Int = 0,
    val examFinished: Boolean = false,
    val selectedOptionIndex: Int? = null,
    val coinBalance: Int = 0
)
