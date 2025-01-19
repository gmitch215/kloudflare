package dev.gmitch215.kloudflare.user.accounts

import kotlinx.serialization.SerialName

/**
 * Represents settings for an [Account].
 * @param abuseContactEmail The email address to contact for abuse reports.
 * @param enforceTwoFactor Whether two-factor authentication is enforced for the account.
 */
data class AccountSettings(
    @SerialName("abuse_contact_email")
    val abuseContactEmail: String? = null,
    @SerialName("enforce_twofactor")
    val enforceTwoFactor: Boolean = false
)
