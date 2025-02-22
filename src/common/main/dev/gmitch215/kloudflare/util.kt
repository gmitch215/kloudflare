package dev.gmitch215.kloudflare

internal fun String.appendParameter(name: String, param: Any?, char: Char = '&'): String = if (param != null) "$this${char}$name=$param" else this