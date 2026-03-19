package app.translator.data.local

import app.translator.core_res.R
import app.translator.domain.model.ConversationPhrase
import app.translator.domain.model.ExamQuestion
import app.translator.domain.model.GrammarLesson
import app.translator.domain.model.LearnModule
import app.translator.domain.model.LearnModuleType
import app.translator.domain.model.ListenItem
import app.translator.domain.model.VocabularyWord
import javax.inject.Inject

class LearnLocalDataSource @Inject constructor() {

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
            VocabularyWord(R.string.vocab_hola, R.string.vocab_hello),
            VocabularyWord(R.string.vocab_gracias, R.string.vocab_thank_you),
            VocabularyWord(R.string.vocab_por_favor, R.string.vocab_please),
            VocabularyWord(R.string.vocab_buenos_dias, R.string.vocab_good_morning),
            VocabularyWord(R.string.vocab_buenas_noches, R.string.vocab_good_night),
            VocabularyWord(R.string.vocab_como_estas, R.string.vocab_how_are_you),
            VocabularyWord(R.string.vocab_adios, R.string.vocab_goodbye)
        )
    }

    fun getGrammarLessons(): List<GrammarLesson> {
        return listOf(
            GrammarLesson(
                titleResId = R.string.grammar_ser_estar_title,
                explanationResId = R.string.grammar_ser_estar_explanation,
                exampleResId = R.string.grammar_ser_estar_example
            ),
            GrammarLesson(
                titleResId = R.string.grammar_ar_verbs_title,
                explanationResId = R.string.grammar_ar_verbs_explanation,
                exampleResId = R.string.grammar_ar_verbs_example
            ),
            GrammarLesson(
                titleResId = R.string.grammar_gender_title,
                explanationResId = R.string.grammar_gender_explanation,
                exampleResId = R.string.grammar_gender_example
            )
        )
    }

    fun getExamQuestions(): List<ExamQuestion> {
        return listOf(
            ExamQuestion(
                questionResId = R.string.exam_q1,
                optionResIds = listOf(R.string.exam_q1_opt1, R.string.exam_q1_opt2, R.string.exam_q1_opt3, R.string.exam_q1_opt4),
                correctIndex = 0
            ),
            ExamQuestion(
                questionResId = R.string.exam_q2,
                optionResIds = listOf(R.string.exam_q2_opt1, R.string.exam_q2_opt2, R.string.exam_q2_opt3, R.string.exam_q2_opt4),
                correctIndex = 0
            ),
            ExamQuestion(
                questionResId = R.string.exam_q3,
                optionResIds = listOf(R.string.exam_q3_opt1, R.string.exam_q3_opt2, R.string.exam_q3_opt3, R.string.exam_q3_opt4),
                correctIndex = 0
            )
        )
    }

    fun getListenItems(): List<ListenItem> {
        return listOf(
            ListenItem(R.string.listen_donde_bano, R.string.listen_where_bathroom),
            ListenItem(R.string.listen_cuanto_cuesta, R.string.listen_how_much),
            ListenItem(R.string.listen_necesito_ayuda, R.string.listen_need_help),
            ListenItem(R.string.listen_puede_repetir, R.string.listen_can_repeat)
        )
    }

    fun getConversationPhrases(): List<ConversationPhrase> {
        return listOf(
            ConversationPhrase(R.string.conv_mucho_gusto, R.string.conv_nice_to_meet),
            ConversationPhrase(R.string.conv_puede_ayudar, R.string.conv_can_help),
            ConversationPhrase(R.string.conv_buscando_hotel, R.string.conv_looking_hotel),
            ConversationPhrase(R.string.conv_mesa_para_dos, R.string.conv_table_for_two)
        )
    }
}
