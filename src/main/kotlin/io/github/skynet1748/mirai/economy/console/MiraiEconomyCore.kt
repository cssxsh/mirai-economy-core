package io.github.skynet1748.mirai.economy.console

import net.mamoe.mirai.console.plugin.jvm.*
import net.mamoe.mirai.utils.*

public object MiraiEconomyCore : KotlinPlugin(
    JvmPluginDescription(
        id = "io.github.skynet1748.mirai-economy-core",
        name = "mirai-economy-core",
        version = "1.0.0-dev",
    ) {
        author("skynet1748")
    }
) {
    override fun onEnable() {
        logger.info { "Plugin loaded" }
    }
}