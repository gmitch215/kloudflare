package dev.gmitch215.kloudflare

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.js.Js
import kotlinx.browser.window
import kotlinx.coroutines.Dispatchers

actual val Kloudflare.engine: HttpClientEngine
    get() = Js.create {
        pipelining = true
        dispatcher = Dispatchers.Default
    }

fun isNodeJs(): Boolean = js("typeof process !== 'undefined' && process.versions && process.versions.node") as Boolean

actual val Kloudflare.userAgent: String
    get() = if (isNodeJs()) "Kloudflare/1.0, ${OS.platform()} ${OS.release()}" else "Kloudflare/1.0, ${window.navigator.userAgent}"

@JsModule("os")
external object OS {
    fun platform(): String
    fun release(): String
}