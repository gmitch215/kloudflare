package dev.gmitch215.kloudflare

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.js.Js
import kotlinx.coroutines.Dispatchers

actual val Kloudflare.engine: HttpClientEngine
    get() = Js.create {
        pipelining = true
        dispatcher = Dispatchers.Default
    }

actual val Kloudflare.userAgent: String
    get() = "Kloudflare/1.0, WASM"