package dev.gmitch215.kloudflare

internal fun String.appendParameter(name: String, param: Any?, char: Char = '&'): String = if (param != null) "${char}name=$param" else this