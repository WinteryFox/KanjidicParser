package foxie.kanjidic.parser

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText

data class Character(
        var literal: String,
        @JacksonXmlElementWrapper
        var codepoint: List<CpValue>
)

data class CpValue(
        @JacksonXmlProperty(isAttribute = true)
        var cp_type: String
) {
        @JacksonXmlText
        lateinit var cp_value: String private set
}