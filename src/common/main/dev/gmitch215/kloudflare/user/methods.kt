@file:JvmName("Users")

package dev.gmitch215.kloudflare.user

import dev.gmitch215.kloudflare.Kloudflare
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmName

/**
 * Represents a type of report that can be submitted.
 */
@Serializable
enum class ReportType {
    /**
     * Report a DMCA violation.
     */
    @SerialName("abuse_dmca")
    ABUSE_DMCA,

    /**
     * Report a trademark violation.
     */
    @SerialName("abuse_trademark")
    ABUSE_TRADEMARK,

    /**
     * Report a general abuse violation.
     */
    @SerialName("abuse_general")
    ABUSE_GENERAL,

    /**
     * Report a phishing violation.
     */
    @SerialName("abuse_phishing")
    ABUSE_PHISHING,

    /**
     * Report a violation of child protection laws.
     */
    @SerialName("abuse_children")
    ABUSE_CHILDREN,

    /**
     * Report a threat of abuse.
     */
    @SerialName("abuse_threat")
    ABUSE_THREAT,

    /**
     * Report a violation of registrar or WHOIS abuse policies.
     */
    @SerialName("abuse_registrar_whois")
    ABUSE_REGISTRAR_WHOIS,

    /**
     * Report a violation of the Non-Consensual Sexual Explicit Imagery (NCSEI) policy.
     */
    @SerialName("abuse_ncsei")
    ABUSE_NCSEI
}

/**
 * Represents the type of notification to send when submitting a report.
 */
@Serializable
enum class ReportNotificationType {
    /**
     * Send a notification to the user.
     */
    @SerialName("send")
    SEND,

    /**
     * Send a notification to the user anonymously.
     *
     * Note that DMCA and trademark reports cannot be sent anonymously.
     */
    @SerialName("send_anon")
    SEND_ANONYMOUS,

    /**
     * Do not send a notification.
     */
    @SerialName("none")
    NONE
}

/**
 * Represents an abuse report to be submitted.
 *
 * The only required fields are the [email] field and at least one URL.
 */
class AbuseReport(
    /**
     * The type of report to submit.
     */
    internal val type: ReportType,
    /**
     * The email address to send notifications to.
     */
    var email: String = "",
    /**
     * Whether the user agrees to the terms of service.
     */
    var agree: Boolean = true,
    /**
     * The notification type for the host.
     */
    var hostNotification: ReportNotificationType = ReportNotificationType.SEND,
    /**
     * The notification type for the National Center for Missing and Exploited Children.
     */
    var ncmecNotification: ReportNotificationType = ReportNotificationType.SEND,
    /**
     * The notification type for the owner.
     */
    var ownerNotification: ReportNotificationType = ReportNotificationType.SEND,
    /**
     * The URLs to report.
     */
    val urls: MutableList<String> = mutableListOf(),
    /**
     * The address of the agent.
     */
    var address: String? = null,
    /**
     * The name of the agent.
     */
    var agent: String? = null,
    /**
     * The city of the agent.
     */
    var city: String? = null,
    /**
     * Additional comments.
     */
    var comments: String? = null,
    /**
     * The company of the agent.
     */
    var company: String? = null,
    /**
     * The where the agent resides.
     */
    var country: String? = null,
    /**
     * The destination IPs.
     */
    var destinationIps: List<String>? = null,
    /**
     * The justification for the report.
     */
    var justification: String? = null,
    /**
     * The name of the agent.
     */
    var name: String? = null,
    /**
     * Whether the subject is represented by NCSEI.
     */
    var ncseiSubjectRepresentation: Boolean? = null,
    /**
     * Whether the work is original.
     */
    var originalWork: Boolean? = null,
    /**
     * The ports and protocols. A maximum of 30 can be provided.
     *
     * These should be formated like `80/TCP`, `22/UDP`, etc.
     */
    var portsProtocols: List<String>? = null,
    /**
     * The source IPs. A maximum of 30 IPs can be provided.
     */
    var sourceIps: List<String>? = null,
    /**
     * The state where the agent resides.
     */
    var state: String? = null,
    /**
     * The telephone number of the agent.
     */
    var telephone: String? = null,
    /**
     * The title of the agent.
     */
    var title: String? = null,
    /**
     * The trademark number, if applicable.
     */
    var trademarkNumber: String? = null,
    /**
     * The trademark office, if applicable.
     */
    var trademarkOffice: String? = null,
    /**
     * The trademark symbol, if applicable.
     */
    var trademarkSymbol: String? = null
)

@Serializable
internal class InternalAbuseReport(
    @SerialName("act")
    val type: ReportType,
    val agree: Int,
    val email: String,
    val email2: String = email,
    val urls: String,
    @SerialName("host_notification")
    val hostNotification: ReportNotificationType = ReportNotificationType.SEND,
    @SerialName("ncmec_notification")
    val ncmecNotification: ReportNotificationType = ReportNotificationType.SEND,
    @SerialName("owner_notification")
    val ownerNotification: ReportNotificationType = ReportNotificationType.SEND,
    @SerialName("address1")
    val address: String? = null,
    @SerialName("agent_name")
    val agent: String? = null,
    val city: String? = null,
    val comments: String? = null,
    val company: String? = null,
    val country: String? = null,
    @SerialName("destination_ips")
    val destinationIps: String? = null,
    val justification: String? = null,
    val name: String? = null,
    @SerialName("ncsei_subject_representation")
    val ncseiSubjectRepresentation: Boolean? = null,
    @SerialName("original_work")
    val originalWork: Boolean? = null,
    @SerialName("ports_protocols")
    val portsProtocols: String? = null,
    @SerialName("signature")
    val signature: String? = name,
    @SerialName("source_ips")
    val sourceIps: String? = null,
    val state: String? = null,
    @SerialName("tele")
    val telephone: String? = null,
    val title: String? = null,
    @SerialName("trademark_number")
    val trademarkNumber: String? = null,
    @SerialName("trademark_office")
    val trademarkOffice: String? = null,
    @SerialName("trademark_symbol")
    val trademarkSymbol: String? = null,
) {
    constructor(report: AbuseReport) : this(
        report.type,
        if (report.agree) 1 else 0,
        report.email,
        report.email,
        report.urls.joinToString("\n"),
        report.hostNotification,
        report.ncmecNotification,
        report.ownerNotification,
        report.address,
        report.agent,
        report.city,
        report.comments,
        report.company,
        report.country,
        report.destinationIps?.joinToString("\n"),
        report.justification,
        report.name,
        report.ncseiSubjectRepresentation,
        report.originalWork,
        report.portsProtocols?.joinToString(","),
        report.name,
        report.sourceIps?.joinToString("\n"),
        report.state,
        report.telephone,
        report.title,
        report.trademarkNumber,
        report.trademarkOffice,
        report.trademarkSymbol
    )
}

/**
 * Represents a response from the API when submitting an abuse report.
 * @property type The type of report that was submitted.
 */
@Serializable
class AbuseRequest(
    @SerialName("act")
    val type: ReportType
)

/**
 * Represents a response from the API when submitting an abuse report.
 * @property id The ID of the report.
 * @property request The request that was submitted.
 * @property result The result of the request.
 */
@Serializable
class AbuseReportResponse(
    @SerialName("abuse_rand")
    val id: String,
    val request: AbuseRequest,
    val result: String
)

/**
 * Submits an abuse report for a specific account.
 * @param accountId The ID of the account to submit the report for.
 * @param report The report to submit.
 * @return The response from the API.
 */
suspend fun Kloudflare.submitAbuseReport(accountId: String, report: AbuseReport)
    = post<InternalAbuseReport, AbuseReportResponse>("/accounts/$accountId/abuse_reports/${report.type.name.lowercase()}", InternalAbuseReport(report))

/**
 * Represents an edit user request.
 * @property country The country of the user.
 * @property firstName The first name of the user.
 * @property lastName The last name of the user.
 * @property telephone The telephone number of the user.
 * @property zipcode The zip code of the user.
 */
class EditUser(
    var country: String? = null,
    @SerialName("first_name")
    var firstName: String? = null,
    @SerialName("last_name")
    var lastName: String? = null,
    var telephone: String? = null,
    val zipcode: String? = null,
)

/**
 * Sends an edit user request.
 * @param editUser The edit user request to send.
 * @return The response from the API.
 */
suspend fun Kloudflare.editUser(editUser: EditUser)
    = patch<EditUser, Unit>("/user", editUser)