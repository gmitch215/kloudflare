package dev.gmitch215.kloudflare

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

actual val Kloudflare.engine: HttpClientEngine
    get() = CIO.create {
        pipelining = true
        dispatcher = Dispatchers.IO.limitedParallelism(PARALLEL_COUNT)
    }

actual val Kloudflare.userAgent: String
    get() = "Kloudflare/1.0, Linux"