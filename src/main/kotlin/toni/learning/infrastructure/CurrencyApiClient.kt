package toni.learning.infrastructure


import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import toni.learning.application.ICurrency
import toni.learning.model.Currency


class CurrencyApiClient(private val client: HttpClient): ICurrency {

    private val baseUrl = "https://api.frankfurter.app"

    override suspend fun getAllCurrencies(): List<String> = withContext(Dispatchers.IO) {
        val resp = client.get("$baseUrl/currencies") {
            ContentType.Application.Json
        }

        if (resp.status.value != HttpStatusCode.OK.value) {
            println("Failed to fetch currencies: ${resp.status}")
            return@withContext emptyList()
        }

        val currencies = resp.body<Map<String, String>>()
        currencies.map { Currency(it.value, it.key).toJson() }
    }

    override suspend fun getAllCurrencyCodes(): List<String> = withContext(Dispatchers.IO) {
        val resp = client.get("$baseUrl/currencies") {
            ContentType.Application.Json
        }

        if (resp.status.value != HttpStatusCode.OK.value) {
            println("Failed to fetch currencies: ${resp.status}")
            return@withContext emptyList()
        }

        val currencies = resp.body<Map<String, String>>()
        currencies.keys.toList()
    }

}
