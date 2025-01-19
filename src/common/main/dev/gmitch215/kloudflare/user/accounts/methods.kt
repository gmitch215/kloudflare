@file:JvmName("Accounts")

package dev.gmitch215.kloudflare.user.accounts

import dev.gmitch215.kloudflare.Id
import dev.gmitch215.kloudflare.Kloudflare
import dev.gmitch215.kloudflare.PageDirection
import dev.gmitch215.kloudflare.PageParams
import dev.gmitch215.kloudflare.appendParameter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmName
import kotlin.jvm.JvmOverloads

/**
 * Represents a type of Cloudflare account.
 * @property name The name of the account.
 */
enum class AccountType {
    /**
     * A standard account.
     */
    @SerialName("standard")
    STANDARD,

    /**
     * An enterprise account.
     */
    @SerialName("enterprise")
    ENTERPRISE
}

/**
 * Create an account.
 * @property name The name of the account.
 * @property type The type of account.
 * @property unit The tenant unit of the account, if applicable.
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

/**
 * Deletes an account.
 *
 * This request requires using [Kloudflare.email] and [Kloudflare.apiKey], and does not support [Kloudflare.apiToken].
 * @param accountId The ID of the account to delete.
 * @return The ID of the account that was deleted.
 */
suspend fun Kloudflare.deleteAccount(accountId: String) = delete<Id>("/accounts/$accountId")

/**
 * Lists all the accounts that you are a member of.
 *
 * This request requires using [Kloudflare.email] and [Kloudflare.apiKey], and does not support [Kloudflare.apiToken].
 * @param name The name of the account to filter by, if requested.
 * @param pageParams The parameters for pagination.
 * @return A list of accounts.
 */
@JvmOverloads
suspend fun Kloudflare.listAccounts(name: String? = null, pageParams: PageParams = PageParams()) =
    get<List<Account>>("/accounts?$pageParams".appendParameter("name", name))

/**
 * Update the details of an account.
 * @property name The new name of the account.
 * @property settings The new settings of the account.
 */
@Serializable
data class UpdateAccountDetails(
    var name: String = "",
    var settings: AccountSettings = AccountSettings()
)

/**
 * Update the details of an account.
 *
 * This request requires using [Kloudflare.email] and [Kloudflare.apiKey], and does not support [Kloudflare.apiToken].
 * @param accountId The ID of the account to update.
 * @param details the details of the account to update.
 * @return The account that was updated.
 */
suspend fun Kloudflare.updateAccount(accountId: String, details: UpdateAccountDetails) = put<UpdateAccountDetails, Account>("/accounts/$accountId", details)

/**
 * Update the details of an account.
 *
 * This request requires using [Kloudflare.email] and [Kloudflare.apiKey], and does not support [Kloudflare.apiToken].
 * @param accountId The ID of the account to update.
 * @param details A lambda used to create the details of the account to update.
 * @return The account that was updated.
 */
suspend fun Kloudflare.updateAccount(accountId: String, details: UpdateAccountDetails.() -> Unit) = updateAccount(accountId, UpdateAccountDetails().apply(details))