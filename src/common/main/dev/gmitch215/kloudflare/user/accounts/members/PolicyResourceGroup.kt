package dev.gmitch215.kloudflare.user.accounts.members

import dev.gmitch215.kloudflare.Identifiable
import dev.gmitch215.kloudflare.Nameable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a resource group that a [Policy] applies to.
 * @property id The ID of the resource group.
 * @property scopes The scopes of the resource group.
 * @property metadata The metadata of the resource group.
 * @property name The name of the resource group.
 */
@Serializable
data class PolicyResourceGroup(
    override val id: String,
    @SerialName("scope")
    val scopes: List<PolicyResourceGroupScope>,
    @SerialName("meta")
    val metadata: Map<String, String> = emptyMap(),
    override val name: String = ""
) : Identifiable, Nameable