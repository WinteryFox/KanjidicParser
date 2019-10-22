package foxie.kanjidic.parser

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText

data class Character(
        var cp_value: CpValue
)

data class CpValue(
        @JacksonXmlText
        var value: String,
        @JacksonXmlProperty(isAttribute = true, localName = "cp_type")
        var cp_type: String
)