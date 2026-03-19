package app.translator.data.remote

import javax.inject.Inject

class LectoTranslateRemoteDataSource @Inject constructor(
    private val api: LectoTranslateApiService
) {
    suspend fun translate(text: String, source: String, target: String): TranslateResponse {
        return api.translate(
            TranslateRequest(
                texts = listOf(text),
                from = source,
                to = listOf(target)
            )
        )
    }
}
