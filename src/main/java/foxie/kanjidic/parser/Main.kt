package foxie.kanjidic.parser

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File

fun main() {
    val entries = XmlMapper()
            .registerKotlinModule()
            .readValue(File("../character.xml"), Character::class.java)
            //.readValue(File("../kanjidic2.xml"), Array<Entry>::class.java)
    println(entries.toString())
}