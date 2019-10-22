package foxie.kanjidic.parser

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper

data class Kanjidic(
        var header: Header,
        @JacksonXmlElementWrapper(useWrapping = false)
        @JsonProperty("character")
        var characters: List<Character>
) {
    data class Header(
            @JsonProperty("file_version")
            var fileVersion: String?,
            @JsonProperty("database_version")
            var databaseVersion: String,
            @JsonProperty("date_of_creation")
            var dateOfCreation: String
    )
}