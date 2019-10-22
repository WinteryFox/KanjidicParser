package foxie.kanjidic.parser

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText

data class Character(
        var literal: String,
        @JacksonXmlElementWrapper
        var codepoint: List<Codepoint>,
        @JacksonXmlElementWrapper
        var radical: List<Radical>,
        var misc: Miscellaneous,
        @JacksonXmlElementWrapper
        @JsonProperty("dic_number")
        var numbers: List<Reference>,
        @JacksonXmlElementWrapper
        @JsonProperty("query_code")
        var codes: List<Code>,
        @JsonProperty("reading_meaning")
        var readingMeaning: ReadingMeaning
) {
    data class ReadingMeaning(
            @JsonProperty("rmgroup")
            var group: Group,
            @JacksonXmlElementWrapper(useWrapping = false)
            var nanori: List<String>
    ) {
        data class Group(
                @JacksonXmlElementWrapper(useWrapping = false)
                @JsonProperty("reading")
                var readings: List<Reading>,
                @JacksonXmlElementWrapper(useWrapping = false)
                @JsonProperty("meaning")
                var meanings: List<Meaning>
        ) {
            data class Meaning(
                    @JacksonXmlProperty(isAttribute = true, localName = "m_lang")
                    var lang: String? = null
            ) {
                @JacksonXmlText
                lateinit var value: String
                    private set
            }

            data class Reading(
                    @JacksonXmlProperty(isAttribute = true, localName = "r_type")
                    var type: String?
            ) {
                @JacksonXmlText
                lateinit var value: String
                    private set
            }
        }
    }

    data class Code(
            @JacksonXmlProperty(isAttribute = true, localName = "qc_type")
            var type: String
    ) {
        @JacksonXmlText
        lateinit var value: String
            private set
    }

    data class Reference(
            @JacksonXmlProperty(isAttribute = true, localName = "dr_type")
            var type: String,
            @JacksonXmlProperty(isAttribute = true, localName = "m_vol")
            var volume: String?,
            @JacksonXmlProperty(isAttribute = true, localName = "m_page")
            var page: String?
    ) {
        @JacksonXmlText
        lateinit var value: String
            private set
    }

    data class Miscellaneous(
            var grade: String,
            @JsonProperty("stroke_count")
            var strokeCount: String,
            var variant: Variant,
            @JsonProperty("freq")
            var frequency: String,
            var jlpt: String
    ) {
        data class Variant(
                @JacksonXmlProperty(isAttribute = true, localName = "var_type")
                var type: String
        ) {
            @JacksonXmlText
            lateinit var value: String
                private set
        }
    }

    data class Radical(
            @JacksonXmlProperty(isAttribute = true, localName = "rad_type")
            var type: String
    ) {
        @JacksonXmlText
        lateinit var value: String
            private set
    }

    data class Codepoint(
            @JacksonXmlProperty(isAttribute = true, localName = "cp_type")
            var type: String
    ) {
        @JacksonXmlText
        lateinit var value: String
            private set
    }
}