package dev.gmitch215.kloudflare.user.accounts

import dev.gmitch215.kloudflare.Id
import dev.gmitch215.kloudflare.Kloudflare
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a type of Cloudflare account.
 * @param name The name of the account.
 */
enum class AccountType {
    @SerialName("standard")
    STANDARD,

    @SerialName("enterprise")
    ENTERPRISE
}

/**
 * Details about account creation.
 * @param name The name of the account.
 * @param type The type of account.
 * @param unit The tenant unit of the account, if applicable.
 * @see <a href="https://developers.cloudflare.com/tenant/how-to/manage-accounts/">Products > Tenant > How to > Manage Accounts</a>
 */
@Serializable
data class CreateAccountDetails(
    var name: String = "",
    var type: AccountType = AccountType.STANDARD,
    var unit: Id? = null,
)

/**
 * Create an account (only available for tenant admins at this time).
 *
 * This request requires using [Kloudflare.email] and [Kloudflare.apiKey], and does not support [Kloudflare.apiToken].
 * @param details A lambda used to create the details of the account to create.
 * @return The account that was created.
 */
suspend fun Kloudflare.createAccount(details: CreateAccountDetails.() -> Unit) = createAccount(CreateAccountDetails().apply(details))

/**
 * Create an account (only available for tenant admins at this time).
 *
 * This request requires using [Kloudflare.email] and [Kloudflare.apiKey], and does not support [Kloudflare.apiToken].
 * @param details The details of the account to create.
 * @return The account that was created.
 */
suspend fun Kloudflare.createAccount(details: CreateAccountDetails) = post<CreateAccountDetails, Account>("/accounts", details)

/**
 * Get information about a specific account that you are a member of.
 *
 * This request requires using [Kloudflare.email] and [Kloudflare.apiKey], and does not support [Kloudflare.apiToken].
 * @param accountId The ID of the account to get information about.
 * @return The account information.
 */
suspend fun Kloudflare.getAccountDetails(accountId: String) = get<Account>("/accounts/$accountId")