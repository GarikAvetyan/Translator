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
    @StringRes val spanishResId: Int,
    @StringRes val englishResId: Int
)

data class GrammarLesson(
    @StringRes val titleResId: Int,
    @StringRes val explanationResId: Int,
    @StringRes val exampleResId: Int
)

data class ExamQuestion(
    @StringRes val questionResId: Int,
    val optionResIds: List<Int>,
    val correctIndex: Int
)

data class ListenItem(
    @StringRes val spanishResId: Int,
    @StringRes val englishResId: Int
)

data class ConversationPhrase(
    @StringRes val spanishResId: Int,
    @StringRes val englishResId: Int
)
