package app.translator.data.local

import app.translator.domain.model.PhraseCategory
import app.translator.domain.model.PhraseCategoryType
import app.translator.domain.model.PhraseItem
import app.translator.core_res.R

class PhrasebookLocalDataSource {
    fun getCategories(): List<PhraseCategory> {
        return listOf(
            PhraseCategory(PhraseCategoryType.Greetings, R.string.category_greetings),
            PhraseCategory(PhraseCategoryType.EverydayPhrases, R.string.category_everyday_phrases),
            PhraseCategory(PhraseCategoryType.Transport, R.string.category_transport),
            PhraseCategory(PhraseCategoryType.Travels, R.string.category_travels),
            PhraseCategory(PhraseCategoryType.Hotels, R.string.category_hotels),
            PhraseCategory(PhraseCategoryType.Restaurant, R.string.category_restaurant),
            PhraseCategory(PhraseCategoryType.Directions, R.string.category_directions),
            PhraseCategory(PhraseCategoryType.Health, R.string.category_health),
            PhraseCategory(PhraseCategoryType.Purchases, R.string.category_purchases),
            PhraseCategory(PhraseCategoryType.Emergency, R.string.category_emergency),
            PhraseCategory(PhraseCategoryType.Business, R.string.category_business),
            PhraseCategory(PhraseCategoryType.TimeDate, R.string.category_time_date),
            PhraseCategory(PhraseCategoryType.Numbers, R.string.category_numbers),
            PhraseCategory(PhraseCategoryType.Bank, R.string.category_bank),
            PhraseCategory(PhraseCategoryType.Sports, R.string.category_sports),
            PhraseCategory(PhraseCategoryType.BeautySalon, R.string.category_beauty_salon),
            PhraseCategory(PhraseCategoryType.Family, R.string.category_family),
            PhraseCategory(PhraseCategoryType.Excursions, R.string.category_excursions),
            PhraseCategory(PhraseCategoryType.FriendlyMeeting, R.string.category_friendly_meeting),
            PhraseCategory(PhraseCategoryType.PostOffice, R.string.category_post_office)
        )
    }

    fun getPhrases(type: PhraseCategoryType): List<PhraseItem> {
        return when (type) {
            PhraseCategoryType.Greetings -> listOf(
                PhraseItem("Hola", "Hello"),
                PhraseItem("Buenos dias", "Good morning"),
                PhraseItem("Buenas tardes", "Good afternoon"),
                PhraseItem("Buenas noches", "Good night")
            )
            PhraseCategoryType.EverydayPhrases -> listOf(
                PhraseItem("Por favor", "Please"),
                PhraseItem("Gracias", "Thank you"),
                PhraseItem("De nada", "You are welcome"),
                PhraseItem("Perdon", "Sorry")
            )
            PhraseCategoryType.Transport -> listOf(
                PhraseItem("Necesito un taxi", "I need a taxi"),
                PhraseItem("Donde esta la estacion?", "Where is the station?"),
                PhraseItem("Un billete, por favor", "A ticket, please")
            )
            PhraseCategoryType.Travels -> listOf(
                PhraseItem("Quiero viajar a...", "I want to travel to..."),
                PhraseItem("A que hora sale?", "What time does it leave?"),
                PhraseItem("Tengo una reserva", "I have a reservation")
            )
            PhraseCategoryType.Hotels -> listOf(
                PhraseItem("Tengo una reserva", "I have a reservation"),
                PhraseItem("Necesito una habitacion", "I need a room"),
                PhraseItem("Cuanto cuesta por noche?", "How much per night?")
            )
            PhraseCategoryType.Restaurant -> listOf(
                PhraseItem("Una mesa para dos", "A table for two"),
                PhraseItem("La cuenta, por favor", "The bill, please"),
                PhraseItem("Que recomienda?", "What do you recommend?")
            )
            PhraseCategoryType.Directions -> listOf(
                PhraseItem("Donde esta...", "Where is..."),
                PhraseItem("Como llego a...", "How do I get to..."),
                PhraseItem("Esta cerca?", "Is it near?")
            )
            PhraseCategoryType.Health -> listOf(
                PhraseItem("Necesito un medico", "I need a doctor"),
                PhraseItem("Me duele", "It hurts"),
                PhraseItem("Tengo fiebre", "I have a fever")
            )
            PhraseCategoryType.Purchases -> listOf(
                PhraseItem("Cuanto cuesta?", "How much does it cost?"),
                PhraseItem("Puede bajar el precio?", "Can you lower the price?"),
                PhraseItem("Lo llevo", "I will take it")
            )
            PhraseCategoryType.Emergency -> listOf(
                PhraseItem("Ayuda!", "Help!"),
                PhraseItem("Llame a la policia", "Call the police"),
                PhraseItem("Es una emergencia", "It is an emergency")
            )
            PhraseCategoryType.Business -> listOf(
                PhraseItem("Tengo una reunion", "I have a meeting"),
                PhraseItem("Podemos hablar?", "Can we talk?"),
                PhraseItem("Envieme un correo", "Send me an email")
            )
            PhraseCategoryType.TimeDate -> listOf(
                PhraseItem("Que hora es?", "What time is it?"),
                PhraseItem("Hoy", "Today"),
                PhraseItem("Manana", "Tomorrow")
            )
            PhraseCategoryType.Numbers -> listOf(
                PhraseItem("Uno, dos, tres", "One, two, three"),
                PhraseItem("Diez", "Ten"),
                PhraseItem("Cien", "One hundred")
            )
            PhraseCategoryType.Bank -> listOf(
                PhraseItem("Necesito cambiar dinero", "I need to exchange money"),
                PhraseItem("Donde esta el banco?", "Where is the bank?"),
                PhraseItem("Quiero retirar dinero", "I want to withdraw money")
            )
            PhraseCategoryType.Sports -> listOf(
                PhraseItem("Me gusta el futbol", "I like soccer"),
                PhraseItem("Vamos al estadio", "Lets go to the stadium"),
                PhraseItem("Que equipo?", "Which team?")
            )
            PhraseCategoryType.BeautySalon -> listOf(
                PhraseItem("Necesito un corte de pelo", "I need a haircut"),
                PhraseItem("Solo las puntas", "Just the ends"),
                PhraseItem("No muy corto", "Not too short")
            )
            PhraseCategoryType.Family -> listOf(
                PhraseItem("Mi familia", "My family"),
                PhraseItem("Tengo dos hermanos", "I have two brothers"),
                PhraseItem("Esta es mi madre", "This is my mother")
            )
            PhraseCategoryType.Excursions -> listOf(
                PhraseItem("Quiero hacer una excursion", "I want to take an excursion"),
                PhraseItem("Hay un guia?", "Is there a guide?"),
                PhraseItem("Cuanto dura?", "How long does it take?")
            )
            PhraseCategoryType.FriendlyMeeting -> listOf(
                PhraseItem("Nos vemos luego", "See you later"),
                PhraseItem("Vamos a tomar un cafe", "Lets have a coffee"),
                PhraseItem("Que tal tu dia?", "How was your day?")
            )
            PhraseCategoryType.PostOffice -> listOf(
                PhraseItem("Quiero enviar una carta", "I want to send a letter"),
                PhraseItem("Cuanto cuesta el envio?", "How much is shipping?"),
                PhraseItem("Necesito un sello", "I need a stamp")
            )
        }
    }
}
