package dev.gmitch215.kloudflare

import kotlin.test.Test
import kotlin.test.assertEquals

class TestUtil {

    @Test
    fun testAppendParameter() {
        val url = "https://example.com"

        val newUrl1 = url.appendParameter("key", "value", '?')
        assertEquals("https://example.com?key=value", newUrl1)

        val newUrl2 = newUrl1.appendParameter("key2", null)
        assertEquals("https://example.com?key=value", newUrl2)

        val newUrl3 = newUrl2.appendParameter("key3", "value3")
        assertEquals("https://example.com?key=value&key3=value3", newUrl3)
    }

}