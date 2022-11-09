package xyz.cssxsh.mirai.economy.console

import kotlinx.coroutines.*
import net.mamoe.mirai.console.plugin.jvm.*
import net.mamoe.mirai.event.*
import xyz.cssxsh.mirai.economy.*

@PublishedApi
internal object MiraiEconomyCorePlugin : KotlinPlugin(
    JvmPluginDescription(
        id = "xyz.cssxsh.mirai.plugin.mirai-economy-core",
        name = "mirai-economy-core",
        version = "1.0.0",
    ) {
        author("cssxsh")

        dependsOn("xyz.cssxsh.mirai.plugin.mirai-hibernate-plugin", false)
    }
) {
    override fun onEnable() {
        MiraiEconomyListenerHost.registerTo(globalEventChannel())
        EconomyService.reload(folder = dataFolderPath)
        EconomyService.register(MiraiCoin)
    }

    override fun onDisable() {
        EconomyService.close()
        MiraiEconomyListenerHost.cancel()
    }
}