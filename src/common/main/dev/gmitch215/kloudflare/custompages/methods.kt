package dev.gmitch215.kloudflare.custompages

import dev.gmitch215.kloudflare.Kloudflare
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Fetches the details of a custom page.
 * @param accountsOrZones The accounts or zones to fetch the custom page from.
 * @param accountOrZoneId The ID of the account or zone to fetch the custom page from.
 * @param id The ID of the custom page to fetch.
 */
suspend fun Kloudflare.getCustomPage(accountsOrZones: String, accountOrZoneId: String, id: String)
    = get<String>("$accountsOrZones/custom_pages/$accountOrZoneId/$id")

/**
 * Fetches the list of custom pages.
 * @param accountsOrZones The accounts or zones to fetch the custom pages from.
 * @param accountOrZoneId The ID of the account or zone to fetch the custom pages from.
 * @return The list of custom pages.
 */
suspend fun Kloudflare.listCustomPages(accountsOrZones: String, accountOrZoneId: String)
    = get<Array<String>>("$accountsOrZones/$accountOrZoneId/custom_pages")

/**
 * Represents the state of a custom page.
 */
@Serializable
enum class CustomPageState {
    @SerialName("default")
    DEFAULT,
    @SerialName("customized")
    CUSTOMIZED
}

/**
 * Represents an update to a custom page.
 * @property state The state of the custom page.
 * @property url The URL of the custom page.
 */
@Serializable
data class UpdateCustomPage(
    var state: CustomPageState = CustomPageState.DEFAULT,
    var url: String = ""
)

/**
 * Updates a custom page.
 * @param accountsOrZones The accounts or zones to update the custom page for.
 * @param accountOrZoneId The ID of the account or zone to update the custom page for.
 * @param id The ID of the custom page to update.
 * @param update The update to apply to the custom page.
 * @return The updated custom page.
 */
suspend fun Kloudflare.updateCustomPage(accountsOrZones: String, accountOrZoneId: String, id: String, update: UpdateCustomPage.() -> Unit)
    = updateCustomPage(accountsOrZones, accountOrZoneId, id, UpdateCustomPage().apply(update))

/**
 * Updates a custom page.
 * @param accountsOrZones The accounts or zones to update the custom page for.
 * @param accountOrZoneId The ID of the account or zone to update the custom page for.
 * @param id The ID of the custom page to update.
 * @param update The update to apply to the custom page.
 * @return The updated custom page.
 */
suspend fun Kloudflare.updateCustomPage(accountsOrZones: String, accountOrZoneId: String, id: String, update: UpdateCustomPage)
    = put<UpdateCustomPage, String>("$accountsOrZones/custom_pages/$accountOrZoneId/$id", update)