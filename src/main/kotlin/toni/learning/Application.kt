package toni.learning

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.serialization.json.Json
import toni.learning.api.registerCurrencyRoutes
import toni.learning.infrastructure.CurrencyApiClient
import toni.learning.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "localhost", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureHTTP()
    configureRouting()

    val httpClient = HttpClient(OkHttp) {
        engine {
            config {
                followRedirects(true)
            }
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }
    val currencyService = CurrencyApiClient(httpClient)
    registerCurrencyRoutes(currencyService)
}
