package dev.gmitch215.kloudflare.user.accounts.members

import dev.gmitch215.kloudflare.user.accounts.Account
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a policy for a [Member] in an [Account].
 * @property id The ID of the policy.
 * @property access The access level of the policy. This is either `allow` or `deny`.
 * @property permissionGroups The permission groups of the policy.
 * @property resourceGroups The resource groups of the policy.
 */
@Serializable
data class Policy(
    val id: String,
    val access: String,
    @SerialName("permission_groups")
    val permissionGroups: List<PolicyPermissionGroup> = emptyList(),
    @SerialName("resource_groups")
    val resourceGroups: List<PolicyResourceGroup> = emptyList()
)