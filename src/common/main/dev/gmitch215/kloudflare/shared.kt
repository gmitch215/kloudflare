package dev.gmitch215.kloudflare

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents the result of an API request to Cloudflare.
 * This class is unique to Kloudflare and is not present in the Cloudflare API.
 * @property errors A list of errors that occurred during the request.
 * @property messages A list of messages sent during the request.
 * @property success Whether the request was successful.
 * @property result The result of the request, if any.
 */
@Serializable
data class Result<T>(
    val errors: List<ResponseInfo>,
    val messages: List<ResponseInfo>,
    val success: Boolean = true,
    val result: T? = null
)

/**
 * Represents a response with only an `id` field.
 * This class is unique to Kloudflare and is not present in the Cloudflare API.
 * @property id The ID.
 */
@Serializable
data class Id(val id: String)

/**
 * Represents a response with only a `key` field.
 * This class is unique to Kloudflare and is not present in the Cloudflare API.
 * @property key The key.
 */
@Serializable
data class Key(val key: String)

/**
 * Represents responses from the Cloudflare API.
 * @property status The Cloudflare status code of the response. The minimum value is `1000`.
 * @property message The message of the response.
 * @see <a href="https://developers.cloudflare.com/api/resources/$shared/">Cloudflare API > Shared</a>
 */
@Serializable
data class ResponseInfo(val status: Int, val message: String) {
    init {
        require(status >= 1000) { "Status must be greater than or equal to 1000" }
    }
}