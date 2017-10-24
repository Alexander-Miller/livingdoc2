package org.livingdoc.repositories.format

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class MarkdownFormatTest  {


    @ParameterizedTest(name = "Do the Thing With {0}")
    @ValueSource(strings = arrayOf("FOO", "BAR", "BAZ"))
    fun `test foo`(s: String) {
        println(s)
    }
//    @ParameterizedTest(name = "\"{0}\"")
//    @ValueSource(strings = arrayOf("", " ", "foo", "foo bar"))
//    fun `any string value is returned as is`(value: String) {
//        assertThat(cut.convert(value, null, null)).isEqualTo(value)
//    }
//
//    @Test fun `converter can converted to Kotlin String`() {
//        assertThat(cut.canConvertTo(String::class.java)).isTrue()
//    }

}
