package toni.learning.api

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import toni.learning.application.ICurrency

fun Application.registerCurrencyRoutes(currencyService: ICurrency) {
    routing {
        get("/currencies") {
            call.respond(currencyService.getAllCurrencies().toString())
        }
        get("/currency-codes") {
            call.respond(currencyService.getAllCurrencyCodes().toString())
        }
    }
}
