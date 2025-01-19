package dev.gmitch215.kloudflare.user.accounts.roles

import dev.gmitch215.kloudflare.user.accounts.members.PermissionGrant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a list of permissions present in a [Role].
 * @property analytics The permissions for analytics.
 * @property billing The permissions for billing.
 * @property cachePurge The permissions for cache purging.
 * @property dns The permissions for DNS.
 * @property dnsRecords The permissions for DNS records.
 * @property loadBalancing The permissions for load balancing.
 * @property logs The permissions for logs.
 * @property organization The permissions for organization.
 * @property ssl The permissions for SSL.
 * @property waf The permissions for WAF.
 * @property zoneSettings The permissions for zone settings.
 * @property zones The permissions for zones.
 */
@Serializable
data class RolePermissions(
    val analytics: PermissionGrant = PermissionGrant(),
    val billing: PermissionGrant = PermissionGrant(),
    @SerialName("cache_purge")
    val cachePurge: PermissionGrant = PermissionGrant(),
    val dns: PermissionGrant = PermissionGrant(),
    @SerialName("dns_records")
    val dnsRecords: PermissionGrant = PermissionGrant(),
    @SerialName("lb")
    val loadBalancing: PermissionGrant = PermissionGrant(),
    val logs: PermissionGrant = PermissionGrant(),
    val organization: PermissionGrant = PermissionGrant(),
    val ssl: PermissionGrant = PermissionGrant(),
    val waf: PermissionGrant = PermissionGrant(),
    @SerialName("zone_settings")
    val zoneSettings: PermissionGrant = PermissionGrant(),
    val zones: PermissionGrant = PermissionGrant()
)