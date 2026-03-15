package app.translator.domain.repository

import app.translator.domain.model.ConversationPhrase
import app.translator.domain.model.ExamQuestion
import app.translator.domain.model.GrammarLesson
import app.translator.domain.model.LearnModule
import app.translator.domain.model.ListenItem
import app.translator.domain.model.VocabularyWord

interface LearnRepository {
    fun getLearnModules(): List<LearnModule>
    fun getVocabularyWords(): List<VocabularyWord>
    fun getGrammarLessons(): List<GrammarLesson>
    fun getExamQuestions(): List<ExamQuestion>
    fun getListenItems(): List<ListenItem>
    fun getConversationPhrases(): List<ConversationPhrase>
}
