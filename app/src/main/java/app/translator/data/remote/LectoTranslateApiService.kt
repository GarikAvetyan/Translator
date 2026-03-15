package app.translator.data.remote

import retrofit2.http.Body
import retrofit2.http.POST

interface LectoTranslateApiService {
    @POST("v1/translate/text")
    suspend fun translate(@Body request: TranslateRequest): TranslateResponse
}
