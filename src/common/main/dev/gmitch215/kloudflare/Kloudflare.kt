package dev.gmitch215.kloudflare

import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * The Root URL for the Cloudflare API.
 */
const val ROOT_URL = "https://api.cloudflare.com/client/v4"

internal const val PARALLEL_COUNT = 16

/**
 * The main entrypoint for the Cloudflare API.
 */
class Kloudflare(
    /**
     * The email address associated with the Cloudflare account.
     *
     * This is passed ot the `X-Auth-Email` header. It is required for specific API use cases, and used in conjunction with [apiKey].
     */
    val email: String? = null,
    /**
     * An API key used to authenticate with the Cloudflare API.
     *
     * This is passed to the `X-Auth-Key` header. It is required for specific API use cases, and used in conjunction with [email].
     */
    val apiKey: String? = null,
    /**
     * An API token used to authenticate with the Cloudflare API.
     *
     * This is passed to the `Authorization` header. It is used in place of [email] and [apiKey] for specific API use cases, and is usually preferred if supported.
     */
    val apiToken: String? = null,
    /**
     * A lambda that is called when the [Kloudflare] instance is created.
     */
    apply: Kloudflare.() -> Unit = {}
) {

    val client = HttpClient(engine) {
        expectSuccess = false

        headers {
            append("User-Agent", userAgent)

            if (email != null)
                append("X-Auth-Email", email)

            if (apiKey != null)
                append("X-Auth-Key", apiKey)

            if (apiToken != null)
                append("Authorization", "Bearer $apiToken")
        }
    }

    /**
     * Create a new instance of [Kloudflare] with an API token.
     * @param apiToken The API token to use.
     * @param apply A lambda that is called when the [Kloudflare] instance is created.
     */
    constructor(apiToken: String, apply: Kloudflare.() -> Unit = {}) : this(email = null, apiToken = apiToken, apply = apply)

    /**
     * Create a new instance of [Kloudflare] with an email and API key.
     * @param email The email address associated with the Cloudflare account.
     * @param apiKey The API key to use.
     * @param apply A lambda that is called when the [Kloudflare] instance is created.
     */
    constructor(email: String, apiKey: String, apply: Kloudflare.() -> Unit = {}) : this(email = email, apiKey = apiKey, apiToken = null, apply = apply)

    init {
        apply()
    }

    /**
     * Performs a request to the Cloudflare API.
     * @param T The type of the result.
     * @param subUrl The sub-URL to request.
     * @param block A lambda that is called to configure the request.
     * @return The result of the request, or null if the request returned 404.
     * @throws KloudflareException If the request failed.
     */
    suspend fun <T> request(subUrl: String, block: HttpRequestBuilder.() -> Unit = {}): Result<T>? {
        val res = client.request("$ROOT_URL${subUrl}", block)

        if (res.status.value == 404)
            return null

        if (res.status.value == 403)
            throw KloudflareException("Request failed with status code 403: Forbidden. Check your API token, email, and API key.")

        if (!res.status.isSuccess())
            throw KloudflareException("Request failed with status code ${res.status.value}")

        return res.body<Result<T>>()
    }

    /**
     * Performs a GET request to the Cloudflare API.
     * @param T The type of the result.
     * @param subUrl The sub-URL to request.
     * @param block A lambda that is called to configure the request.
     * @return The result of the request.
     * @throws KloudflareException If the request failed.
     */
    suspend fun <T> get(subUrl: String, block: HttpRequestBuilder.() -> Unit = {}): T {
        return request<T>(subUrl) {
            method = HttpMethod.Get
            block()
        }?.result ?: throw KloudflareException("No result returned")
    }

    /**
     * Performs a POST request to the Cloudflare API.
     * @param B The type of the body.
     * @param T The type of the result.
     * @param subUrl The sub-URL to request.
     * @param body The body of the request.
     * @param block A lambda that is called to configure the request.
     * @return The result of the request.
     * @throws KloudflareException If the request failed.
     */
    suspend fun <B, T> post(subUrl: String, body: B? = null, block: HttpRequestBuilder.() -> Unit = {}): T {
        return request<T>(subUrl) {
            method = HttpMethod.Post

            if (body != null)
                setBody(body)


            block()
        }?.result ?: throw KloudflareException("No result returned")
    }

    /**
     * Performs a DELETE request to the Cloudflare API.
     * @param T The type of the result.
     * @param subUrl The sub-URL to request.
     * @param block A lambda that is called to configure the request.
     * @return The result of the request.
     * @throws KloudflareException If the request failed.
     */
    suspend fun <T> delete(subUrl: String, block: HttpRequestBuilder.() -> Unit = {}): T {
        return request<T>(subUrl) {
            method = HttpMethod.Delete
            block()
        }?.result ?: throw KloudflareException("No result returned")
    }

}

/**
 * An exception that is thrown when an error occurs during a request to the Cloudflare API.
 * @param message The message of the exception.
 */
class KloudflareException(message: String) : Exception(message)

/**
 * The user agent to use when making requests to the Cloudflare API.
 */
expect val Kloudflare.userAgent: String

/**
 * The Ktor HTTP client engine to use when making requests to the Cloudflare API.
 */
expect val Kloudflare.engine: HttpClientEngine