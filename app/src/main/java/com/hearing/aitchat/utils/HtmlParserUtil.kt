package com.hearing.aitchat.utils

import org.xml.sax.XMLReader
import java.util.*

/**
 * @author liujiadong
 * @since 2020/10/22
 */
object HtmlParserUtil {
    fun parseStart(xmlReader: XMLReader): Map<String, String> {
        val attributes = HashMap<String, String>()
        try {
            val elementField = xmlReader.javaClass.getDeclaredField("theNewElement")
            elementField.isAccessible = true
            val element = elementField[xmlReader]
            val attsField = element.javaClass.getDeclaredField("theAtts")
            attsField.isAccessible = true
            val atts = attsField[element]
            val dataField = atts.javaClass.getDeclaredField("data")
            dataField.isAccessible = true
            val data = dataField[atts] as Array<String>
            val lengthField = atts.javaClass.getDeclaredField("length")
            lengthField.isAccessible = true
            val len = lengthField[atts] as Int
            for (i in 0 until len) {
                attributes[data[i * 5 + 1]] = data[i * 5 + 4]
            }
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        return attributes
    }
}