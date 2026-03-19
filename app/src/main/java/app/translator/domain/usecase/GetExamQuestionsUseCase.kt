package app.translator.domain.usecase

import app.translator.domain.model.ExamQuestion
import app.translator.domain.repository.LearnRepository
import javax.inject.Inject

class GetExamQuestionsUseCase @Inject constructor(
    private val repository: LearnRepository
) {
    operator fun invoke(): List<ExamQuestion> = repository.getExamQuestions()
}
