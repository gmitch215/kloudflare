package dev.gmitch215.kloudflare

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

actual val Kloudflare.engine: HttpClientEngine
    get() = Darwin.create {
        pipelining = true
        dispatcher = Dispatchers.IO.limitedParallelism(PARALLEL_COUNT)

        configureRequest {
            setAllowsCellularAccess(true)
        }

        configureSession {
            setAllowsCellularAccess(true)
        }
    }