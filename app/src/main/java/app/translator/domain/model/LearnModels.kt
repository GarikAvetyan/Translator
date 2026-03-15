package app.translator.domain.model

import androidx.annotation.StringRes

enum class LearnModuleType {
    Vocabulary,
    Grammar,
    Exam,
    Listen,
    Conversation
}

data class LearnModule(
    val type: LearnModuleType,
    @StringRes val titleResId: Int
)

data class VocabularyWord(
    val spanish: String,
    val english: String
)

data class GrammarLesson(
    val title: String,
    val explanation: String,
    val example: String
)

data class ExamQuestion(
    val question: String,
    val options: List<String>,
    val correctIndex: Int
)

data class ListenItem(
    val spanish: String,
    val english: String
)

data class ConversationPhrase(
    val spanish: String,
    val english: String
)
