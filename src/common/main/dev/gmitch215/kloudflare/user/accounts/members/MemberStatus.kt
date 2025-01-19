package dev.gmitch215.kloudflare.user.accounts.members

import dev.gmitch215.kloudflare.user.accounts.Account
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents the status of a [Member] in an [Account].
 */
@Serializable
enum class MemberStatus {
    /**
     * The member has been accepted into the account.
     */
    @SerialName("accepted")
    ACCEPTED,

    /**
     * The member has been invited to the account but has not yet accepted the invitation.
     */
    @SerialName("pending")
    PENDING,

    /**
     * The member has been rejected from the account.
     */
    @SerialName("rejected")
    REJECTED

}