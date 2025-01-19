package dev.gmitch215.kloudflare

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.jetty.Jetty
import kotlinx.coroutines.Dispatchers

actual val Kloudflare.engine: HttpClientEngine
    get() = Jetty.create {
        pipelining = true
        dispatcher = Dispatchers.IO.limitedParallelism(PARALLEL_COUNT)
    }

actual val Kloudflare.userAgent: String
    get() = "Kloudflare/1.0, ${System.getProperty("os.name")} ${System.getProperty("os.version")} ${System.getProperty("os.arch")}"