package toni.learning.model

import kotlinx.serialization.Serializable

/**
 * Represents a currency with a label and a code.
 *
 * @property label The display name of the currency.
 * @property code The international standard code of the currency.
 */
@Serializable
data class Currency(val label: String, val code: String) {

    override fun toString(): String {
        return label
    }

    fun toMap(): Map<String, String> {
        return mapOf("label" to label, "code" to code)
    }

    fun toJson(): String {
        return "{\"label\": \"$label\", \"code\": \"$code\"}"
    }
}
