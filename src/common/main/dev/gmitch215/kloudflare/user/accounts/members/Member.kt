package dev.gmitch215.kloudflare.user.accounts.members

import dev.gmitch215.kloudflare.Identifiable
import dev.gmitch215.kloudflare.user.accounts.Account
import dev.gmitch215.kloudflare.user.accounts.roles.Role
import kotlinx.serialization.Serializable

/**
 * Represents a member of an [Account].
 * @property id The ID of the member.
 * @property roles The roles applied to the member.
 * @property policies The policies applied to the member.
 * @property status The active status of the member.
 * @property user The information for the member, if present.
 */
@Serializable
data class Member(
    override val id: String,
    val roles: List<Role> = emptyList(),
    val policies: List<Policy> = emptyList(),
    val status: MemberStatus = MemberStatus.PENDING,
    val user: MemberInfo? = null
) : Identifiable