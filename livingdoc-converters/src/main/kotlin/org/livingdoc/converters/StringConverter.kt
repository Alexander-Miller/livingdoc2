package org.livingdoc.converters

import org.livingdoc.api.conversion.TypeConverter
import java.lang.reflect.AnnotatedElement


open class StringConverter : TypeConverter<String> {

    override fun convert(value: String, element: AnnotatedElement?, documentClass: Class<*>?): String = value

    override fun canConvertTo(targetType: Class<*>) = String::class.java == targetType
}
