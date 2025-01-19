package dev.gmitch215.kloudflare.user.accounts

import kotlinx.serialization.SerialName

/**
 * Represents a Cloudflare account.
 * @param id The unique identifier of the account.
 * @param name The name of the account.
 * @param creationDate The date the account was created.
 * @param settings The settings for the account.
 */
data class Account(
    val id: String,
    val name: String,
    @SerialName("created_on")
    val creationDate: String? = null,
    val settings: AccountSettings = AccountSettings()
)