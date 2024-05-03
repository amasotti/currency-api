package toni.learning.application

interface ICurrency {
    suspend fun getAllCurrencies(): List<String>
    suspend fun getAllCurrencyCodes(): List<String>
}
