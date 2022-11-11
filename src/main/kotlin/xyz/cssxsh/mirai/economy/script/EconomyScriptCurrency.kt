package xyz.cssxsh.mirai.economy.script

import xyz.cssxsh.mirai.economy.service.*
import java.nio.file.*
import java.util.zip.*
import javax.script.*
import kotlin.io.path.*

/**
 * 自定义的脚本货币
 */
public class EconomyScriptCurrency(
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
        } else {
            script.format(amount, id)
        }
    }

    public companion object {
        /**
         * 从文件夹加载脚本货币
         */
        @JvmStatic
        public fun fromFolder(folder: Path): EconomyScriptCurrency {
            val id = folder.name
            val name = folder.resolve("name.txt").readText()
            val description = folder.resolve("description.txt").readText()
            val format = folder.listDirectoryEntries("format.*").first()

            val manager = ScriptEngineManager(EconomyScriptCurrency::class.java.classLoader)
            val engine = when (val extension = format.extension) {
                "txt" -> null
                else -> manager.getEngineByExtension(extension)
                    ?: throw NoSuchElementException("ScriptEngine: $extension")
            }

            return EconomyScriptCurrency(
                id = id,
                name = name,
                description = description,
                engine = engine,
                script = format.readText()
            )
        }

        /**
         * 从压缩包加载脚本货币
         */
        @JvmStatic
        public fun fromZip(pack: Path): EconomyScriptCurrency {
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
                val manager = ScriptEngineManager(EconomyScriptCurrency::class.java.classLoader)
                engine = when (val extension = format.name.substringAfter(".")) {
                    "txt" -> null
                    else -> manager.getEngineByExtension(extension)
                        ?: throw NoSuchElementException("ScriptEngine: $extension")
                }
                script = zip.getInputStream(format).reader().use { reader ->
                    reader.readText()
                }
            }

            return EconomyScriptCurrency(
                id = id,
                name = name,
                description = description,
                engine = engine,
                script = script
            )
        }
    }
}