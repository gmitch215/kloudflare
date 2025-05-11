package dev.gmitch215.kloudflare.dns.analytics

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DNSReportData(
    @SerialName("dimensions")
    val metricKeys: Array<String>,
    @SerialName("metrics")
    val metricValues: IntArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DNSReportData) return false

        if (!metricKeys.contentEquals(other.metricKeys)) return false
        if (!metricValues.contentEquals(other.metricValues)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = metricKeys.contentHashCode()
        result = 31 * result + metricValues.contentHashCode()
        return result
    }
}