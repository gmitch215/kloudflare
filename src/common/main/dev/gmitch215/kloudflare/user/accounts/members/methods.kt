@file:JvmName("AccountMembers")

package dev.gmitch215.kloudflare.user.accounts.members

import dev.gmitch215.kloudflare.Id
import dev.gmitch215.kloudflare.Kloudflare
import dev.gmitch215.kloudflare.PageParams
import dev.gmitch215.kloudflare.appendParameter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmName
import kotlin.jvm.JvmOverloads

/**
 * Create a member.
 * @property email The email of the member.
 * @property status The active status of the member.
 */
@Serializable
sealed interface CreateMember {
    var email: String
    var status: MemberStatus
}

/**
 * Create a member with roles.
 * @property email The email of the member.
 * @property roles A list of role IDs to apply to the member.
 */
@Serializable
data class CreateMemberWithRoles(
    override var email: String = "",
    override var status: MemberStatus = MemberStatus.PENDING,
    val roles: MutableList<String> = mutableListOf()
) : CreateMember

/**
 * Create a member with policies.
 * @property email The email of the member.
 * @property policies A list of policies to apply to the member.
 */
@Serializable
data class CreateMemberWithPolicies(
    override var email: String = "",
    override var status: MemberStatus = MemberStatus.PENDING,
    val policies: MutableList<Policy> = mutableListOf()
) : CreateMember

/**
 * Create a member.
 *
 * This request requires using [Kloudflare.email] and [Kloudflare.apiKey], and does not support [Kloudflare.apiToken].
 * @param accountId The ID of the account to add the member to.
 * @param member The member to add.
 * @return The member that was added.
 */
suspend fun Kloudflare.addMember(accountId: String, member: CreateMember): Member = post<CreateMember, Member>("accounts/$accountId/members", member)

/**
 * Create a member with roles.
 *
 * This request requires using [Kloudflare.email] and [Kloudflare.apiKey], and does not support [Kloudflare.apiToken].
 * @param accountId The ID of the account to add the member to.
 * @param member A lambda used to create the member to add.
 * @return The member that was added.
 */
suspend fun Kloudflare.addMemberWithRoles(accountId: String, member: CreateMemberWithRoles): Member = addMember(accountId, member)

/**
 * Create a member with roles.
 *
 * This request requires using [Kloudflare.email] and [Kloudflare.apiKey], and does not support [Kloudflare.apiToken].
 * @param accountId The ID of the account to add the member to.
 * @param member The member to add.
 * @return The member that was added.
 */
suspend fun Kloudflare.addMemberWithRoles(accountId: String, member: CreateMemberWithRoles.() -> Unit): Member = addMemberWithRoles(accountId, CreateMemberWithRoles().apply(member))

/**
 * Create a member with policies.
 *
 * This request requires using [Kloudflare.email] and [Kloudflare.apiKey], and does not support [Kloudflare.apiToken].
 * @param accountId The ID of the account to add the member to.
 * @param member The member to add.
 * @return The member that was added.
 */
suspend fun Kloudflare.addMemberWithPolicies(accountId: String, member: CreateMemberWithPolicies): Member = addMember(accountId, member)

/**
 * Create a member with policies.
 *
 * This request requires using [Kloudflare.email] and [Kloudflare.apiKey], and does not support [Kloudflare.apiToken].
 * @param accountId The ID of the account to add the member to.
 * @param member A lambda used to create the member to add.
 * @return The member that was added.
 */
suspend fun Kloudflare.addMemberWithPolicies(accountId: String, member: CreateMemberWithPolicies.() -> Unit): Member = addMemberWithPolicies(accountId, CreateMemberWithPolicies().apply(member))

/**
 * Removes a member from an account.
 *
 * This request requires using [Kloudflare.email] and [Kloudflare.apiKey], and does not support [Kloudflare.apiToken].
 * @param accountId The ID of the account to remove the member from.
 * @param memberId The ID of the member to remove.
 * @return The ID of the member that was removed.
 */
suspend fun Kloudflare.removeMember(accountId: String, memberId: String) = delete<Id>("accounts/$accountId/members/$memberId")

/**
 * Get a member.
 *
 * This request requires using [Kloudflare.email] and [Kloudflare.apiKey], and does not support [Kloudflare.apiToken].
 * @param accountId The ID of the account the member is in.
 * @param memberId The ID of the member to get.
 * @return The member.
 */
suspend fun Kloudflare.getMember(accountId: String, memberId: String) = get<Member>("accounts/$accountId/members/$memberId")

/**
 * Represents a sorting method for listing members.
 */
@Serializable
enum class MemberSort {
    /**
     * Sort by the member's first name.
     */
    @SerialName("user.first_name")
    FIRST_NAME,

    /**
     * Sort by the member's last name.
     */
    @SerialName("user.last_name")
    LAST_NAME,

    /**
     * Sort by the member's email.
     */
    @SerialName("user.email")
    EMAIL,

    /**
     * Sort by the member's status.
     */
    @SerialName("status")
    STATUS
}

/**
 * Get all members in an account.
 *
 * This request requires using [Kloudflare.email] and [Kloudflare.apiKey], and does not support [Kloudflare.apiToken].
 * @param accountId The ID of the account to get members from.
 * @param order The order to sort the members by.
 * @param status The status of the members to filter by.
 * @param pageParams The parameters for pagination.
 * @return A list of members in the account.
 */
@JvmOverloads
suspend fun Kloudflare.listMembers(accountId: String, order: MemberSort? = null, status: MemberStatus? = null, pageParams: PageParams = PageParams()) =
    get<List<Member>>(
        "accounts/$accountId/members?$pageParams"
            .appendParameter("order", order)
            .appendParameter("status", status)
    )

/**
 * Update a member with roles.
 * @property id The ID of the member.
 * @property roles A list of role IDs to apply to the member.
 * @property status The active status of the member.
 * @property user The information for the member.
 */
@Serializable
class UpdateMemberWithRoles(
    var id: String? = null,
    val roles: MutableList<String> = mutableListOf(),
    var status: MemberStatus? = null,
    var user: MemberInfo? = null
)

/**
 * Update a member with policies.
 * @property policies A list of policies to apply to the member.
 */
@Serializable
class UpdateMemberWithPolicies(
    val policies: MutableList<Policy> = mutableListOf(),
)

/**
 * Update a member.
 *
 * This request requires using [Kloudflare.email] and [Kloudflare.apiKey], and does not support [Kloudflare.apiToken].
 * @param accountId The ID of the account the member is in.
 * @param member The member to update.
 * @return The updated member.
 */
suspend fun Kloudflare.updateMember(accountId: String, member: UpdateMemberWithRoles) = put<UpdateMemberWithRoles, Member>("accounts/$accountId/members/${member.id}", member)

/**
 * Update a member.
 *
 * This request requires using [Kloudflare.email] and [Kloudflare.apiKey], and does not support [Kloudflare.apiToken].
 * @param accountId The ID of the account the member is in.
 * @param member A lambda used to update the member.
 * @return The updated member.
 */
suspend fun Kloudflare.updateMember(accountId: String, member: UpdateMemberWithRoles.() -> Unit) = updateMember(accountId, UpdateMemberWithRoles().apply(member))

/**
 * Update a member.
 *
 * This request requires using [Kloudflare.email] and [Kloudflare.apiKey], and does not support [Kloudflare.apiToken].
 * @param accountId The ID of the account the member is in.
 * @param member The member to update.
 * @return The updated member.
 */
suspend fun Kloudflare.updateMember(accountId: String, member: UpdateMemberWithPolicies) = put<UpdateMemberWithPolicies, Member>("accounts/$accountId/members/${member.policies.first().id}", member)

/**
 * Update a member.
 *
 * This request requires using [Kloudflare.email] and [Kloudflare.apiKey], and does not support [Kloudflare.apiToken].
 * @param accountId The ID of the account the member is in.
 * @param member A lambda used to update the member.
 * @return The updated member.
 */
suspend fun Kloudflare.updateMember(accountId: String, member: UpdateMemberWithPolicies.() -> Unit) = updateMember(accountId, UpdateMemberWithPolicies().apply(member))