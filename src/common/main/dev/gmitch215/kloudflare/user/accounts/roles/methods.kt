@file:JvmName("AccountRoles")

package dev.gmitch215.kloudflare.user.accounts.roles

import dev.gmitch215.kloudflare.Kloudflare
import kotlin.jvm.JvmName

/**
 * Gets information about a specific role for an account.
 *
 * This request requires using [Kloudflare.email] and [Kloudflare.apiKey], and does not support [Kloudflare.apiToken].
 * @param accountId The ID of the account.
 * @param roleId The ID of the role.
 * @return The role.
 */
suspend fun Kloudflare.getRole(accountId: String, roleId: String) = get<Role>("accounts/$accountId/roles/$roleId")

/**
 * Gets a list of roles for an account.
 *
 * This request requires using [Kloudflare.email] and [Kloudflare.apiKey], and does not support [Kloudflare.apiToken].
 * @param accountId The ID of the account.
 * @return A list of roles.
 */
suspend fun Kloudflare.getRoles(accountId: String) = get<List<Role>>("accounts/$accountId/roles")