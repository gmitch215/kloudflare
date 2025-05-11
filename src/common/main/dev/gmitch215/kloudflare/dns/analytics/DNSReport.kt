package dev.gmitch215.kloudflare.dns.analytics

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a DNS report.
 * @property data The data of the DNS report.
 * @property dataLag The number of seconds between current time and last processed event.
 * @property rows The number of rows in the report.
 */
@Serializable
data class DNSReport(
    val data: Array<DNSReportData>,
    @SerialName("data_lag")
    val dataLag: Int,
    val rows: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DNSReport) return false

        if (dataLag != other.dataLag) return false
        if (rows != other.rows) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = dataLag
        result = 31 * result + rows
        result = 31 * result + data.contentHashCode()
        return result
    }
}