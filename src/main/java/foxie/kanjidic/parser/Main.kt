package foxie.kanjidic.parser

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.net.URL
import java.sql.BatchUpdateException
import java.sql.DriverManager
import java.util.zip.GZIPInputStream

class Main

fun String?.asSql() = if (this == null) "NULL" else "'${this.replace("'", "''")}'"

fun main(args: Array<String>) {
    if (args.size != 4) {
        println("This program takes 4 arguments in the following order: the ip of the server, the database name, the username, the password.\n" +
                "For example: java -jar kanjidicparser.jar \"localhost\" \"kanjidic\" \"foxie\" \"test123\"")
        return
    }

    println("Downloading Kanjidic2 from the internet...")
    val file = GZIPInputStream(URL("http://www.edrdg.org/kanjidic/kanjidic2.xml.gz").openStream())

    println("Deserializing Kanjidic2...")
    val kanjidic = XmlMapper()
            .registerKotlinModule()
            .readValue(file, Kanjidic::class.java)

    println("Inserting into PostgreSQL...")
    DriverManager
            .getConnection("jdbc:postgresql://${args[0]}/${args[1]}?user=${args[2]}&password=${args[3]}")
            .use { connection ->
                connection.createStatement().use {
                    it.execute(Main::class.java.getResource("/tables.sql").readText())
                }

                connection.createStatement().use { statement ->
                    kanjidic.characters.forEach { character ->
                        statement.addBatch("INSERT INTO character (literal) VALUES (${character.literal.asSql()})")

                        character.codepoint.forEach {
                            statement.addBatch("INSERT INTO codepoint (character, type, codepoint) VALUES (${character.literal.asSql()}, ${it.type.asSql()}, ${it.value.asSql()})")
                        }

                        character.radical.forEach {
                            statement.addBatch("INSERT INTO radical (character, type, radical) VALUES (${character.literal.asSql()}, ${it.type.asSql()}, ${it.value.asSql()})")
                        }

                        statement.addBatch("INSERT INTO miscellaneous (character, grade, stroke_count, frequency, variant_type, variant, jlpt, radical_name) VALUES (${character.literal.asSql()}, ${character.misc.grade}, ${character.misc.strokeCount}, ${character.misc.frequency}, ${character.misc.variant?.type.asSql()}, ${character.misc.variant?.value.asSql()}, ${character.misc.jlpt}, ${character.misc.radicalName.asSql()})")

                        character.references?.forEach {
                            statement.addBatch("INSERT INTO dictionary (character, dictionary, volume, page, value) VALUES (${character.literal.asSql()}, ${it.type.asSql()}, ${it.volume}, ${it.page}, ${it.value.asSql()}) ON CONFLICT DO NOTHING")
                        }

                        character.codes?.forEach {
                            statement.addBatch("INSERT INTO code (character, type, code) VALUES (${character.literal.asSql()}, ${it.type.asSql()}, ${it.value.asSql()})")
                        }

                        character.readingMeaning?.group?.meanings?.forEach {
                            statement.addBatch("INSERT INTO meaning (character, language, meaning) VALUES (${character.literal.asSql()}, ${if (it.lang == null) "en".asSql() else it.lang.asSql()}, ${it.value.asSql()})")
                        }

                        character.readingMeaning?.group?.readings?.forEach {
                            statement.addBatch("INSERT INTO reading (character, type, reading) VALUES (${character.literal.asSql()}, ${it.type.asSql()}, ${it.value.asSql()})")
                        }

                        character.readingMeaning?.nanori?.forEach {
                            statement.addBatch("INSERT INTO nanori (character, nanori) VALUES (${character.literal.asSql()}, ${it.asSql()})")
                        }
                    }

                    try {
                        statement.executeBatch()
                    } catch (e: BatchUpdateException) {
                        e.nextException?.printStackTrace()
                    }
                }
            }

    println("Done!")
}