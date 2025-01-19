package dev.gmitch215.kloudflare

import kotlinx.serialization.Serializable

/**
 * Represents the result of an API request to Cloudflare.
 * This class is unique to Kloudflare and is not present in the Cloudflare API.
 * @param errors A list of errors that occurred during the request.
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
 * @param id The ID.
 */
@Serializable
data class Id(val id: String)

/**
 * Represents responses from the Cloudflare API.
 * @param status The Cloudflare status code of the response. The minimum value is `1000`.
 * @param message The message of the response.
 * @see <a href="https://developers.cloudflare.com/api/resources/$shared/">Cloudflare API > Shared</a>
 */
@Serializable
data class ResponseInfo(val status: Int, val message: String) {
    init {
        require(status >= 1000) { "Status must be greater than or equal to 1000" }
    }
}