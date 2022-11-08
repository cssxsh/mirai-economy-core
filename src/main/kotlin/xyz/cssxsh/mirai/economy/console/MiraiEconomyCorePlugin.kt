package xyz.cssxsh.mirai.economy.console

import kotlinx.coroutines.*
import net.mamoe.mirai.console.plugin.jvm.*
import net.mamoe.mirai.event.*
import xyz.cssxsh.mirai.economy.*

@PublishedApi
internal object MiraiEconomyCorePlugin : KotlinPlugin(
    JvmPluginDescription(
        id = "io.github.skynet1748.mirai-economy-core",
        name = "mirai-economy-core",
        version = "1.0.0-dev",
    ) {
        author("skynet1748")
    }
) {
    override fun onEnable() {
        MiraiEconomyListenerHost.registerTo(globalEventChannel())
        EconomyService.reload()
    }

    override fun onDisable() {
        EconomyService.close()
        MiraiEconomyListenerHost.cancel()
    }
}