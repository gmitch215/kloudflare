package dev.gmitch215.kloudflare

internal fun String.appendParameter(name: String, param: Any?, char: Char = '&'): String = if (param != null) "$this${char}$name=$param" else this
internal fun String.appendParameters(params: Map<String, Any?>): String {
    var result = this
    var char = '?'
    for ((name, param) in params) {
        if (param == null) continue
        result = "$result$char$name=$param"
        char = '&'
    }

    return result
}