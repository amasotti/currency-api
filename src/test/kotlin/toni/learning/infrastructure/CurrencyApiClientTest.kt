package toni.learning.infrastructure

import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Test
import kotlin.test.*


class CurrencyApiClientTest {

    private fun createMockClient(content: String, status: HttpStatusCode = HttpStatusCode.OK): HttpClient {

        val mockEngine = MockEngine { request ->
            respond(
                content = ByteReadChannel(content),
                status = status,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }

        return  HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }
    }

    @Test
    fun testGetAllCurrenciesSuccessful() = runBlocking {
        val jsonContent = """
            {
                "USD": "United States Dollar (USD)",
                "EUR": "Euro (EUR)"
            }
        """.trimIndent()
        val client = createMockClient(jsonContent.toString(), HttpStatusCode.OK)
        val currencyService = CurrencyApiClient(client)

        val result = currencyService.getAllCurrencies()

        assertEquals(2, result.size)
        assertContains(result, """{"label": "United States Dollar (USD)", "code": "USD"}""")
        assertContains(result, """{"label": "Euro (EUR)", "code": "EUR"}""")
    }

    @Test
    fun testGetAllCurrenciesFailure() = runBlocking {
        val client = createMockClient("{}", HttpStatusCode.BadRequest)
        val currencyService = CurrencyApiClient(client)

        val result = currencyService.getAllCurrencies()

        assertTrue { result.isEmpty() }
    }
}
