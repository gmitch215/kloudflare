@file:OptIn(DelicateCoroutinesApi::class)

package dev.gmitch215.kloudflare

import io.ktor.client.engine.*
import io.ktor.client.engine.js.*
import kotlinx.browser.window
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers

actual val Kloudflare.engine: HttpClientEngine
    get() = Js.create {
        pipelining = true
        dispatcher = Dispatchers.Default
    }

actual val Kloudflare.userAgent: String
    get() = "Kloudflare/1.0, ${window.navigator.userAgent}"