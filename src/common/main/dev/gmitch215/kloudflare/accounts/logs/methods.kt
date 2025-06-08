package dev.gmitch215.kloudflare.accounts.logs

import dev.gmitch215.kloudflare.IdAndName
import dev.gmitch215.kloudflare.Identifiable
import dev.gmitch215.kloudflare.Kloudflare
import dev.gmitch215.kloudflare.Nameable
import dev.gmitch215.kloudflare.PageDirection
import dev.gmitch215.kloudflare.Typed
import dev.gmitch215.kloudflare.user.accounts.Account
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents an entry in the audit log.
 * @property id The unique identifier of the audit log entry.
 * @property account The account associated with the audit log entry.
 * @property action The action performed in the audit log entry.
 */
@Serializable
data class AuditLogEntry(
    override val id: String,
    val account: Account? = null,
    val action: AuditLogAction? = null,
    val actor: AuditLogActor? = null,
    @SerialName("raw")
    val connection: AuditLogConnection? = null,
    val zone: IdAndName? = null,
) : Identifiable

/**
 * Represents an action in the audit log.
 * @property description The description of the action.
 * @property result The result of the action.
 * @property time The time the action was performed.
 * @property type The type of the action.
 */
@Serializable
data class AuditLogAction(
    val description: String,
    val result: String,
    val time: String,
    override val type: String,
) : Typed

/**
 * Represents the actor who performed an action in the audit log.
 * @property id The unique identifier of the actor.
 * @property context The context in which the actor is operating (e.g., "dash" for dashboard, "api_token" for API token).
 * @property type The type of the actor (e.g., user, system).
 * @property name The name of the actor.
 * @property email The email of the actor, if applicable.
 * @property ip The IP address of the actor, if applicable.
 * @property token The name of the token used by the actor, if applicable.
 * @property tokenId The token ID of the actor, if applicable.
 */
@Serializable
data class AuditLogActor(
    override val id: String,
    val context: String = "dash",
    override val type: String = "account",
    override val name: String = "",
    val email: String? = null,
    @SerialName("ip_address")
    val ip: String? = null,
    @SerialName("token_name")
    val token: String? = null,
    @SerialName("token_id")
    val tokenId: String? = null,
) : Identifiable, Nameable, Typed

/**
 * Represents the raw connection information in the audit log between the request and the response.
 * This includes details such as the Cloudflare Ray ID, HTTP method, status code, URI, and user agent.
 * @property rayId The Cloudflare Ray ID associated with the request.
 * @property method The HTTP method used for the request (e.g., GET, POST).
 * @property code The HTTP status code returned in the response.
 * @property uri The URI of the request.
 * @property userAgent The user agent string of the client making the request, if available.
 */
@Serializable
data class AuditLogConnection(
    @SerialName("cf_ray_id")
    val rayId: String,
    val method: String,
    @SerialName("status_code")
    val code: Int,
    val uri: String,
    @SerialName("user_agent")
    val userAgent: String? = null,
)

/**
 * Represents the affected resource in the audit log.
 * @property id The unique identifier of the resource.
 * @property product The product associated with the resource (e.g., "dns", "firewall").
 * @property request The request parameters associated with the resource.
 * @property response The response parameters associated with the resource.
 * @property scope The scope of the resource, which may include additional context or metadata.
 * @property type The type of the resource affected.
 */
@Serializable
data class AuditLogResource(
    override val id: String,
    val product: String,
    val request: Map<String, String> = emptyMap(),
    val response: Map<String, String> = emptyMap(),
    val scope: Map<String, String> = emptyMap(),
    override val type: String,
) : Identifiable, Typed

/**
 * Fetches audit logs for a specific account.
 * @param accountId The ID of the account to fetch audit logs for.
 * @param before The timestamp before which to fetch logs.
 * @param after The timestamp after which to fetch logs.
 * @param direction The direction of the page (default is descending).
 * @param limit The maximum number of logs to return (default is 100).
 * @return An array of [AuditLogEntry] representing the audit logs.
 */
suspend fun Kloudflare.getAuditLogs(
    accountId: String,
    before: String,
    after: String,
    direction: PageDirection = PageDirection.DESCENDING,
    limit: Int = 100,
) = get<Array<AuditLogEntry>>("/accounts/$accountId/logs/audit?before=$before&after=$after&direction=$direction&limit=$limit")