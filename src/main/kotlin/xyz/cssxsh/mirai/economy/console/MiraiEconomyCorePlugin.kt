package xyz.cssxsh.mirai.economy.console

import net.mamoe.mirai.console.plugin.jvm.*
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
        EconomyService.reload()
        globalEconomy {
            service.basket.forEach { (_, currency) ->
                println(currency.name)
                println(currency.description)
            }
        }
    }
}