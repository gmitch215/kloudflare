package dev.gmitch215.kloudflare

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a direction for paginated requests.
 */
@Serializable
enum class PageDirection(private val toString: String) {
    /**
     * Ascending order.
     */
    @SerialName("asc")
    ASCENDING("asc"),

    /**
     * Descending order.
     */
    @SerialName("desc")
    DESCENDING("desc")

    ;

    override fun toString(): String = toString
}

/**
 * Represents parameters for paginated requests.
 * @param direction The direction of the page.
 * @param page The page number.
 * @param perPage The number of items per page.
 */
@Serializable
open class PageParams(
    val direction: PageDirection = PageDirection.DESCENDING,
    val page: Int = 1,
    val perPage: Int = 20
) {
    override fun toString(): String = "direction=$direction&page=$page&per_page=$perPage"
}