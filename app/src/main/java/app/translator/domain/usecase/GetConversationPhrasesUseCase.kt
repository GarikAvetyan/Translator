package app.translator.domain.usecase

import app.translator.domain.model.ConversationPhrase
import app.translator.domain.repository.LearnRepository

class GetConversationPhrasesUseCase(
    private val repository: LearnRepository
) {
    operator fun invoke(): List<ConversationPhrase> = repository.getConversationPhrases()
}
