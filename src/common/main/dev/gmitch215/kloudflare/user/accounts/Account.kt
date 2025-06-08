package dev.gmitch215.kloudflare.user.accounts

import dev.gmitch215.kloudflare.Identifiable
import dev.gmitch215.kloudflare.Nameable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a Cloudflare account.
 * @property id The unique identifier of the account.
 * @property name The name of the account.
 * @property creationDate The date the account was created.
 * @property settings The settings for the account.
 */
@Serializable
data class Account(
    override val id: String,
    override val name: String,
    @SerialName("created_on")
    val creationDate: String? = null,
    val settings: AccountSettings = AccountSettings()
) : Identifiable, Nameable