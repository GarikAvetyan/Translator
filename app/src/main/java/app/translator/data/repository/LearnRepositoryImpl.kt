package app.translator.data.repository

import app.translator.data.local.LearnLocalDataSource
import app.translator.domain.model.ConversationPhrase
import app.translator.domain.model.ExamQuestion
import app.translator.domain.model.GrammarLesson
import app.translator.domain.model.LearnModule
import app.translator.domain.model.ListenItem
import app.translator.domain.model.VocabularyWord
import app.translator.domain.repository.LearnRepository
import javax.inject.Inject

class LearnRepositoryImpl @Inject constructor(
    private val localDataSource: LearnLocalDataSource
) : LearnRepository {
    override fun getLearnModules(): List<LearnModule> = localDataSource.getLearnModules()

    override fun getVocabularyWords(): List<VocabularyWord> = localDataSource.getVocabularyWords()

    override fun getGrammarLessons(): List<GrammarLesson> = localDataSource.getGrammarLessons()

    override fun getExamQuestions(): List<ExamQuestion> = localDataSource.getExamQuestions()

    override fun getListenItems(): List<ListenItem> = localDataSource.getListenItems()

    override fun getConversationPhrases(): List<ConversationPhrase> = localDataSource.getConversationPhrases()
}
