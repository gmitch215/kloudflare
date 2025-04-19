package dev.gmitch215.kloudflare

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents the result of an API request to Cloudflare.
 * This class represents the "Envelope" type of the Cloudflare API.
 * @property errors A list of errors that occurred during the request.
 * @property messages A list of messages sent during the request.
 * @property success Whether the request was successful.
 * @property result The result of the request, if any.
 */
@Serializable
data class Result<T>(
    val errors: List<ResponseInfo> = emptyList(),
    val messages: List<ResponseInfo> = emptyList(),
    val success: Boolean = true,
    val result: T? = null,
    @SerialName("result_info")
    val resultInfo: ResultInfo? = null,
)

/**
 * Represents optional result information for paginated results.
 * @property count The number of items in the current page.
 * @property page The current page number.
 * @property perPage The number of items per page.
 * @property totalCount The total number of items across all pages.
 */
@Serializable
data class ResultInfo(
    val count: Int = 0,
    val page: Int = 1,
    @SerialName("per_page")
    val perPage: Int = 0,
    @SerialName("total_count")
    val totalCount: Int = 0
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