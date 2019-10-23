package foxie.kanjidic.parser

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File
import java.sql.BatchUpdateException
import java.sql.DriverManager

class Main

fun String?.asSql() = if (this == null) "NULL" else "'${this.replace("'", "''")}'"

fun main() {
    val kanjidic = XmlMapper()
            .registerKotlinModule()
            .readValue(File("../kanjidic2.xml"), Kanjidic::class.java)

    val connection = DriverManager
            .getConnection("jdbc:postgresql://localhost/kanjidic?user=postgres&password=test123")

    connection.createStatement()
            .execute(Main::class.java.getResource("/tables.sql").readText())

    val statement = connection.createStatement()

    kanjidic.characters.forEach { character ->
        statement.addBatch("INSERT INTO character (literal) VALUES ('${character.literal}')")

        character.codepoint.forEach {
            statement.addBatch("INSERT INTO codepoint (character, type, codepoint) VALUES (lastval(), ${it.type.asSql()}, ${it.value.asSql()})")
        }

        character.radical.forEach {
            statement.addBatch("INSERT INTO radical (character, type, radical) VALUES (lastval(), ${it.type.asSql()}, ${it.value.asSql()})")
        }

        statement.addBatch("INSERT INTO miscellaneous (character, grade, stroke_count, frequency, variant_type, variant, jlpt, radical_name) VALUES (lastval(), ${character.misc.grade}, ${character.misc.strokeCount}, ${character.misc.frequency}, ${character.misc.variant?.type.asSql()}, ${character.misc.variant?.value.asSql()}, ${character.misc.jlpt}, ${character.misc.radicalName.asSql()})")

        character.references?.forEach {
            statement.addBatch("INSERT INTO dictionary (character, dictionary, volume, page, value) VALUES (lastval(), ${it.type.asSql()}, ${it.volume}, ${it.page}, ${it.value.asSql()})")
        }

        character.codes?.forEach {
            statement.addBatch("INSERT INTO code (character, type, code) VALUES (lastval(), ${it.type.asSql()}, ${it.value.asSql()})")
        }

        character.readingMeaning?.group?.meanings?.forEach {
            statement.addBatch("INSERT INTO meaning (character, language, reading) VALUES (lastval(), ${if (it.lang == null) "en".asSql() else it.lang.asSql()}, ${it.value.asSql()})");
        }

        character.readingMeaning?.group?.readings?.forEach {
            statement.addBatch("INSERT INTO reading (character, type, reading) VALUES (lastval(), ${it.type.asSql()}, ${it.value.asSql()})")
        }

        character.readingMeaning?.nanori?.forEach {
            statement.addBatch("INSERT INTO nanori (character, nanori) VALUES (lastval(), ${it.asSql()})")
        }
    }

    try {
        statement.executeBatch()
    } catch (e: BatchUpdateException) {
        println(e.updateCounts.size)
        e.nextException.printStackTrace()
    }
}