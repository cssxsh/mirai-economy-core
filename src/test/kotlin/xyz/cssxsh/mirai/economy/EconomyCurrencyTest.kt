package xyz.cssxsh.mirai.economy

import org.junit.jupiter.api.*
import xyz.cssxsh.mirai.economy.script.*
import java.nio.file.Path
import javax.script.*

internal class EconomyCurrencyTest {
    private val manager = ScriptEngineManager()
    private val data: Path = Path.of("example", "currencies")

    @Test
    fun engine() {
        for (factory in manager.engineFactories) {
            println("${factory.languageName} / ${factory.languageVersion}")
        }
    }

//    org.luaj:luaj-jse:3.0.1

    @Test
    fun lua() {
        val currency = EconomyScriptCurrency.fromFolder(folder = data.resolve("Lua"))
        Assertions.assertEquals("100 枚 Lua", currency.format(amount = 100.0))
    }

//    com.ibm.icu:icu4j:71.1
//    org.graalvm.js:js-scriptengine:22.2.0
//    org.graalvm.js:js:22.2.0
//    org.graalvm.regex:regex:22.2.0
//    org.graalvm.sdk:graal-sdk:22.2.0
//    org.graalvm.truffle:truffle-api:22.2.0

    @Test
    fun js() {
        val currency = EconomyScriptCurrency.fromFolder(folder = data.resolve("ECMAScript"))
        Assertions.assertEquals("100 块 ECMAScript", currency.format(amount = 100.0))
    }

//    org.python:jython-standalone:2.7.3

    @Test
    fun python() {
        val currency = EconomyScriptCurrency.fromFolder(folder = data.resolve("Python"))
        Assertions.assertEquals("100.0 个 Python", currency.format(amount = 100.0))
    }

    @Test
    fun txt() {
        val currency = EconomyScriptCurrency.fromFolder(folder = data.resolve("Him188"))
        Assertions.assertEquals("114514 位 Him188 鸽子", currency.format(amount = 114514.0))
    }
}