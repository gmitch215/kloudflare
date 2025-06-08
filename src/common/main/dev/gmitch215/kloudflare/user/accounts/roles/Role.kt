package dev.gmitch215.kloudflare.user.accounts.roles

import dev.gmitch215.kloudflare.Identifiable
import dev.gmitch215.kloudflare.Nameable
import dev.gmitch215.kloudflare.user.accounts.Account
import dev.gmitch215.kloudflare.user.accounts.members.Member
import kotlinx.serialization.Serializable

/**
 * Represents a role that a [Member] can have in an [Account].
 * @property id The ID of the role.
 * @property description The description of the role.
 * @property name The human-readable name of the role.
 * @property permissions The permissions of the role.
 */
@Serializable
data class Role(
    override val id: String,
    val description: String,
    override val name: String,
    val permissions: RolePermissions
) : Identifiable, Nameable