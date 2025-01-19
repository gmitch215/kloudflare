package dev.gmitch215.kloudflare

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.winhttp.WinHttp
import io.ktor.http.HttpProtocolVersion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

actual val Kloudflare.engine: HttpClientEngine
    get() = WinHttp.create {
        pipelining = true
        protocolVersion = HttpProtocolVersion.HTTP_2_0
        dispatcher = Dispatchers.IO.limitedParallelism(PARALLEL_COUNT)
    }

actual val Kloudflare.userAgent: String
    get() = "Kloudflare/1.0, Windows"