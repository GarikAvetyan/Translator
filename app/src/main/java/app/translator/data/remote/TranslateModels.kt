package app.translator.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TranslateRequest(
    @SerialName("texts") val texts: List<String>,
    @SerialName("to") val to: List<String>,
    @SerialName("from") val from: String
)

@Serializable
data class TranslateResponse(
    @SerialName("translations") val translations: List<TranslationGroup> = emptyList()
)

@Serializable
data class TranslationGroup(
    @SerialName("to") val to: String,
    @SerialName("translated") val translated: List<String> = emptyList()
)
