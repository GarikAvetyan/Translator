package app.translator.data.local

import app.translator.core_res.R
import app.translator.domain.model.ConversationPhrase
import app.translator.domain.model.ExamQuestion
import app.translator.domain.model.GrammarLesson
import app.translator.domain.model.LearnModule
import app.translator.domain.model.LearnModuleType
import app.translator.domain.model.ListenItem
import app.translator.domain.model.VocabularyWord

class LearnLocalDataSource {
    fun getLearnModules(): List<LearnModule> {
        return listOf(
            LearnModule(LearnModuleType.Vocabulary, R.string.learn_vocab),
            LearnModule(LearnModuleType.Grammar, R.string.learn_grammar),
            LearnModule(LearnModuleType.Exam, R.string.learn_exam),
            LearnModule(LearnModuleType.Listen, R.string.learn_listen),
            LearnModule(LearnModuleType.Conversation, R.string.learn_conversation)
        )
    }

    fun getVocabularyWords(): List<VocabularyWord> {
        return listOf(
            VocabularyWord("Hola", "Hello"),
            VocabularyWord("Gracias", "Thank you"),
            VocabularyWord("Por favor", "Please"),
            VocabularyWord("Buenos dias", "Good morning"),
            VocabularyWord("Buenas noches", "Good night"),
            VocabularyWord("Como estas", "How are you?"),
            VocabularyWord("Adios", "Goodbye")
        )
    }

    fun getGrammarLessons(): List<GrammarLesson> {
        return listOf(
            GrammarLesson(
                title = "Ser vs Estar",
                explanation = "Use ser for permanent traits and estar for temporary states.",
                example = "Ella es doctora. Ella esta cansada."
            ),
            GrammarLesson(
                title = "Present Tense - AR Verbs",
                explanation = "Remove -ar and add: o, as, a, amos, an.",
                example = "Yo hablo, tu hablas, el habla."
            ),
            GrammarLesson(
                title = "Gender and Articles",
                explanation = "Most nouns ending in -o are masculine, -a are feminine.",
                example = "el libro, la mesa."
            )
        )
    }

    fun getExamQuestions(): List<ExamQuestion> {
        return listOf(
            ExamQuestion(
                question = "How do you say 'Thank you' in Spanish?",
                options = listOf("Gracias", "Hola", "Adios", "Por favor"),
                correctIndex = 0
            ),
            ExamQuestion(
                question = "Choose the correct form: Yo ___ (hablar).",
                options = listOf("hablo", "hablas", "habla", "hablan"),
                correctIndex = 0
            ),
            ExamQuestion(
                question = "Translate: 'Good night'",
                options = listOf("Buenas noches", "Buenos dias", "Buenas tardes", "Hasta luego"),
                correctIndex = 0
            )
        )
    }

    fun getListenItems(): List<ListenItem> {
        return listOf(
            ListenItem("Donde esta el bano?", "Where is the bathroom?"),
            ListenItem("Cuanto cuesta?", "How much does it cost?"),
            ListenItem("Necesito ayuda", "I need help"),
            ListenItem("Puede repetir, por favor?", "Can you repeat, please?")
        )
    }

    fun getConversationPhrases(): List<ConversationPhrase> {
        return listOf(
            ConversationPhrase("Mucho gusto", "Nice to meet you"),
            ConversationPhrase("Me puede ayudar?", "Can you help me?"),
            ConversationPhrase("Estoy buscando un hotel", "I am looking for a hotel"),
            ConversationPhrase("Quiero una mesa para dos", "I want a table for two")
        )
    }
}
