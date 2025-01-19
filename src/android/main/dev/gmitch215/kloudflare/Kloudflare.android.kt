package dev.gmitch215.kloudflare

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.Dispatchers
import java.util.concurrent.TimeUnit

actual val Kloudflare.engine: HttpClientEngine
    get() = OkHttp.create {
        pipelining = true
        dispatcher = Dispatchers.IO.limitedParallelism(PARALLEL_COUNT)

        config {
            followRedirects(true)
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
        }
    }

actual val Kloudflare.userAgent: String
    get() = "Kloudflare/1.0, Android"