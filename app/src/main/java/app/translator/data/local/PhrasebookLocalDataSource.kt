package app.translator.data.local

import app.translator.core_res.R
import app.translator.domain.model.PhraseCategory
import app.translator.domain.model.PhraseCategoryType
import app.translator.domain.model.PhraseItem
import javax.inject.Inject

class PhrasebookLocalDataSource @Inject constructor() {

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
                PhraseItem(R.string.phrase_hola, R.string.phrase_hello),
                PhraseItem(R.string.phrase_buenos_dias, R.string.phrase_good_morning),
                PhraseItem(R.string.phrase_buenas_tardes, R.string.phrase_good_afternoon),
                PhraseItem(R.string.phrase_buenas_noches, R.string.phrase_good_night)
            )
            PhraseCategoryType.EverydayPhrases -> listOf(
                PhraseItem(R.string.phrase_por_favor, R.string.phrase_please),
                PhraseItem(R.string.phrase_gracias, R.string.phrase_thank_you),
                PhraseItem(R.string.phrase_de_nada, R.string.phrase_you_are_welcome),
                PhraseItem(R.string.phrase_perdon, R.string.phrase_sorry)
            )
            PhraseCategoryType.Transport -> listOf(
                PhraseItem(R.string.phrase_necesito_taxi, R.string.phrase_need_taxi),
                PhraseItem(R.string.phrase_donde_estacion, R.string.phrase_where_station),
                PhraseItem(R.string.phrase_billete_por_favor, R.string.phrase_ticket_please)
            )
            PhraseCategoryType.Travels -> listOf(
                PhraseItem(R.string.phrase_quiero_viajar, R.string.phrase_want_travel),
                PhraseItem(R.string.phrase_que_hora_sale, R.string.phrase_what_time_leave),
                PhraseItem(R.string.phrase_tengo_reserva, R.string.phrase_have_reservation)
            )
            PhraseCategoryType.Hotels -> listOf(
                PhraseItem(R.string.phrase_tengo_reserva_hotel, R.string.phrase_have_reservation_hotel),
                PhraseItem(R.string.phrase_necesito_habitacion, R.string.phrase_need_room),
                PhraseItem(R.string.phrase_cuanto_por_noche, R.string.phrase_how_much_night)
            )
            PhraseCategoryType.Restaurant -> listOf(
                PhraseItem(R.string.phrase_mesa_para_dos, R.string.phrase_table_for_two),
                PhraseItem(R.string.phrase_la_cuenta, R.string.phrase_the_bill),
                PhraseItem(R.string.phrase_que_recomienda, R.string.phrase_what_recommend)
            )
            PhraseCategoryType.Directions -> listOf(
                PhraseItem(R.string.phrase_donde_esta, R.string.phrase_where_is),
                PhraseItem(R.string.phrase_como_llego, R.string.phrase_how_get_to),
                PhraseItem(R.string.phrase_esta_cerca, R.string.phrase_is_near)
            )
            PhraseCategoryType.Health -> listOf(
                PhraseItem(R.string.phrase_necesito_medico, R.string.phrase_need_doctor),
                PhraseItem(R.string.phrase_me_duele, R.string.phrase_it_hurts),
                PhraseItem(R.string.phrase_tengo_fiebre, R.string.phrase_have_fever)
            )
            PhraseCategoryType.Purchases -> listOf(
                PhraseItem(R.string.phrase_cuanto_cuesta, R.string.phrase_how_much_cost),
                PhraseItem(R.string.phrase_bajar_precio, R.string.phrase_lower_price),
                PhraseItem(R.string.phrase_lo_llevo, R.string.phrase_will_take_it)
            )
            PhraseCategoryType.Emergency -> listOf(
                PhraseItem(R.string.phrase_ayuda, R.string.phrase_help),
                PhraseItem(R.string.phrase_llame_policia, R.string.phrase_call_police),
                PhraseItem(R.string.phrase_es_emergencia, R.string.phrase_its_emergency)
            )
            PhraseCategoryType.Business -> listOf(
                PhraseItem(R.string.phrase_tengo_reunion, R.string.phrase_have_meeting),
                PhraseItem(R.string.phrase_podemos_hablar, R.string.phrase_can_we_talk),
                PhraseItem(R.string.phrase_envieme_correo, R.string.phrase_send_email)
            )
            PhraseCategoryType.TimeDate -> listOf(
                PhraseItem(R.string.phrase_que_hora_es, R.string.phrase_what_time),
                PhraseItem(R.string.phrase_hoy, R.string.phrase_today),
                PhraseItem(R.string.phrase_manana, R.string.phrase_tomorrow)
            )
            PhraseCategoryType.Numbers -> listOf(
                PhraseItem(R.string.phrase_uno_dos_tres, R.string.phrase_one_two_three),
                PhraseItem(R.string.phrase_diez, R.string.phrase_ten),
                PhraseItem(R.string.phrase_cien, R.string.phrase_one_hundred)
            )
            PhraseCategoryType.Bank -> listOf(
                PhraseItem(R.string.phrase_cambiar_dinero, R.string.phrase_exchange_money),
                PhraseItem(R.string.phrase_donde_banco, R.string.phrase_where_bank),
                PhraseItem(R.string.phrase_retirar_dinero, R.string.phrase_withdraw_money)
            )
            PhraseCategoryType.Sports -> listOf(
                PhraseItem(R.string.phrase_gusta_futbol, R.string.phrase_like_soccer),
                PhraseItem(R.string.phrase_vamos_estadio, R.string.phrase_go_stadium),
                PhraseItem(R.string.phrase_que_equipo, R.string.phrase_which_team)
            )
            PhraseCategoryType.BeautySalon -> listOf(
                PhraseItem(R.string.phrase_corte_pelo, R.string.phrase_need_haircut),
                PhraseItem(R.string.phrase_solo_puntas, R.string.phrase_just_ends),
                PhraseItem(R.string.phrase_no_muy_corto, R.string.phrase_not_too_short)
            )
            PhraseCategoryType.Family -> listOf(
                PhraseItem(R.string.phrase_mi_familia, R.string.phrase_my_family),
                PhraseItem(R.string.phrase_tengo_hermanos, R.string.phrase_have_brothers),
                PhraseItem(R.string.phrase_esta_mi_madre, R.string.phrase_this_mother)
            )
            PhraseCategoryType.Excursions -> listOf(
                PhraseItem(R.string.phrase_quiero_excursion, R.string.phrase_want_excursion),
                PhraseItem(R.string.phrase_hay_guia, R.string.phrase_is_guide),
                PhraseItem(R.string.phrase_cuanto_dura, R.string.phrase_how_long)
            )
            PhraseCategoryType.FriendlyMeeting -> listOf(
                PhraseItem(R.string.phrase_nos_vemos, R.string.phrase_see_you),
                PhraseItem(R.string.phrase_tomar_cafe, R.string.phrase_have_coffee),
                PhraseItem(R.string.phrase_que_tal_dia, R.string.phrase_how_was_day)
            )
            PhraseCategoryType.PostOffice -> listOf(
                PhraseItem(R.string.phrase_enviar_carta, R.string.phrase_send_letter),
                PhraseItem(R.string.phrase_cuanto_envio, R.string.phrase_how_much_shipping),
                PhraseItem(R.string.phrase_necesito_sello, R.string.phrase_need_stamp)
            )
        }
    }
}
