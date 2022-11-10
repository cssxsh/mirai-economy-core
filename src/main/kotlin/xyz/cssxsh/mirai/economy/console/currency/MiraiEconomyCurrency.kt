package xyz.cssxsh.mirai.economy.console.currency

import xyz.cssxsh.mirai.economy.service.*
import java.nio.file.*
import java.util.zip.ZipFile
import javax.script.*
import kotlin.io.path.*

@PublishedApi
internal class MiraiEconomyCurrency(
    override val id: String,
    override val name: String,
    override val description: String,
    private val engine: ScriptEngine?,
    private val script: String
) : EconomyCurrency {

    override fun format(amount: Double): String {
        return if (engine != null) {
            val bindings = engine.createBindings()
            bindings["id"] = id
            bindings["name"] = name
            bindings["description"] = description
            bindings["amount"] = amount
            engine.eval(script, bindings).toString()
        } else{
            script.format(amount, id)
        }
    }

    companion object {
        fun fromFolder(folder: Path): MiraiEconomyCurrency {
            val id = folder.name
            val name = folder.resolve("name.txt").readText()
            val description = folder.resolve("description.txt").readText()
            val format = folder.listDirectoryEntries("format.*").first()

            val manager = ScriptEngineManager(MiraiEconomyCurrency::class.java.classLoader)
            val engine = when(val extension = format.extension) {
                "txt" -> null
                else -> manager.getEngineByExtension(extension) ?: throw NoSuchElementException("ScriptEngine: $extension")
            }

            return MiraiEconomyCurrency(
                id = id,
                name = name,
                description = description,
                engine = engine,
                script = format.readText()
            )
        }

        fun fromZip(pack: Path): MiraiEconomyCurrency {
            val id = pack.name.substringBefore(".")
            val name: String
            val description: String
            val engine: ScriptEngine?
            val script: String

            ZipFile(pack.toFile()).use { zip ->
                name = zip.getInputStream(zip.getEntry("name.txt")).reader().use { reader ->
                    reader.readText()
                }
                description = zip.getInputStream(zip.getEntry("description.txt")).reader().use { reader ->
                    reader.readText()
                }
                val format = zip.entries().asIterator().asSequence().first { it.name.startsWith("format.") }
                val manager = ScriptEngineManager(MiraiEconomyCurrency::class.java.classLoader)
                engine = when(val extension = format.name.substringAfter(".")) {
                    "txt" -> null
                    else -> manager.getEngineByExtension(extension) ?: throw NoSuchElementException("ScriptEngine: $extension")
                }
                script = zip.getInputStream(format).reader().use { reader ->
                    reader.readText()
                }
            }

            return MiraiEconomyCurrency(
                id = id,
                name = name,
                description = description,
                engine = engine,
                script = script
            )
        }
    }
}