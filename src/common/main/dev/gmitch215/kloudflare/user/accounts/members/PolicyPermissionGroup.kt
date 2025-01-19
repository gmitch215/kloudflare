package dev.gmitch215.kloudflare.user.accounts.members

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a group of permissions for a [Policy].
 * @property id The ID of the group.
 * @property metadata Additional metadata for the group.
 * @property name The name of the group.
 */
@Serializable
data class PolicyPermissionGroup(
    val id: String,
    @SerialName("meta")
    val metadata: Map<String, String> = emptyMap(),
    val name: String = "",
)