package dev.gmitch215.kloudflare.user.accounts.members

import dev.gmitch215.kloudflare.user.accounts.Account
import kotlinx.serialization.Serializable

/**
 * Represents the permission grant status for a [Member] in an [Account].
 * @property read Whether the member has read access.
 * @property write Whether the member has write access.
 */
@Serializable
data class PermissionGrant(
    val read: Boolean = false,
    val write: Boolean = false,
)