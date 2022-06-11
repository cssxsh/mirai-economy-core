package io.github.skynet1748.mirai.economy

import net.mamoe.mirai.console.data.PluginData
import net.mamoe.mirai.console.plugin.jvm.*
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.utils.*
import java.io.File

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
        loadData(SimpleData)
    }

    // 在 AbstractJvmPlugin 的 PluginData.reload() 无法被重载
    // 此为 mamoe/mirai#2088 临时解决方案
    @OptIn(ConsoleExperimentalApi::class)
    public fun loadData(data : PluginData) {
        File(dataFolder, data.saveName).parentFile?.mkdirs()
        loader.dataStorage.load(this, data)
    }
}