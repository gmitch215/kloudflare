package dev.gmitch215.kloudflare.dns.analytics

import dev.gmitch215.kloudflare.Kloudflare
import dev.gmitch215.kloudflare.appendParameters

/**
 * Gets a DNS report for a specific zone.
 * @param zoneId The ID of the zone to get the report for.
 * @param metricKeys A comma-separated list of dimensions (metric key identifiers) to group results by.
 * @param filters Segmentation filter in 'attribute operator value' format.
 * @param limit The maximum number of results to return.
 * @param metricValues A comma-separated list of metrics queries to return.
 * @param since The start time of the report.
 * @param sort A comma-separated list of dimensions to sort by, where each dimension may be prefixed by - (descending) or + (ascending).
 * @param until The end time of the report.
 */
suspend fun Kloudflare.getDNSReport(
    zoneId: String,
    metricKeys: String? = null,
    filters: String? = null,
    limit: Int? = null,
    metricValues: String? = null,
    since: String? = null,
    sort: String? = null,
    until: String? = null
) = get<DNSReport>("/zones/$zoneId/dns_analytics/report"
    .appendParameters(mapOf(
        "dimensions" to metricKeys,
        "filters" to filters,
        "limit" to limit,
        "metrics" to metricValues,
        "since" to since,
        "sort" to sort,
        "until" to until
    )))