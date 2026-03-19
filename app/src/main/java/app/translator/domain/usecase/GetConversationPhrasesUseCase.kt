package app.translator.domain.usecase

import app.translator.domain.model.ConversationPhrase
import app.translator.domain.repository.LearnRepository
import javax.inject.Inject

class GetConversationPhrasesUseCase @Inject constructor(
    private val repository: LearnRepository
) {
    operator fun invoke(): List<ConversationPhrase> = repository.getConversationPhrases()
}
