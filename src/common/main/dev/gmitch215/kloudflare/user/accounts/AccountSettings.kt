package dev.gmitch215.kloudflare.user.accounts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents settings for an [Account].
 * @property abuseContactEmail The email address to contact for abuse reports.
 * @property enforceTwoFactor Whether two-factor authentication is enforced for the account.
 */
@Serializable
data class AccountSettings(
    @SerialName("abuse_contact_email")
    val abuseContactEmail: String? = null,
    @SerialName("enforce_twofactor")
    val enforceTwoFactor: Boolean = false
)
