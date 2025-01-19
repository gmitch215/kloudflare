package dev.gmitch215.kloudflare.user.accounts.members

import dev.gmitch215.kloudflare.Key
import kotlinx.serialization.Serializable

/**
 * Represents a scope inside a [PolicyResourceGroup].
 * @property key The key of the scope. It is a combination of the pre-defined resource name and identifier.
 * @property objects A list of scope objects for additional context. The key value is a combined of the pre-defined resource name and identifier.
 */
@Serializable
data class PolicyResourceGroupScope(
    val key: String,
    val objects: List<Key>
)