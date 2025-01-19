package dev.gmitch215.kloudflare.user.accounts.members

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents information about a [Member].
 * @property email The email of the member.
 * @property id The id of the member.
 * @property firstName The first name of the member.
 * @property lastName The last name of the member.
 * @property twoFactor If two-factor authentication is enabled.
 */
@Serializable
data class MemberInfo(
    val email: String,
    val id: String = "",
    @SerialName("first_name")
    val firstName: String = "",
    @SerialName("last_name")
    val lastName: String = "",
    @SerialName("two_factor_authentication_enabled")
    val twoFactor: Boolean = false,
)