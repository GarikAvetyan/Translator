package app.translator.domain.model

import androidx.annotation.StringRes

enum class PhraseCategoryType {
    Greetings,
    EverydayPhrases,
    Transport,
    Travels,
    Hotels,
    Restaurant,
    Directions,
    Health,
    Purchases,
    Emergency,
    Business,
    TimeDate,
    Numbers,
    Bank,
    Sports,
    BeautySalon,
    Family,
    Excursions,
    FriendlyMeeting,
    PostOffice
}

data class PhraseCategory(
    val type: PhraseCategoryType,
    @StringRes val titleResId: Int
)

data class PhraseItem(
    @StringRes val spanishResId: Int,
    @StringRes val englishResId: Int
)
