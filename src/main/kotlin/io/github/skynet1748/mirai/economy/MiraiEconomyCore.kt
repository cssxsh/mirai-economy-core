package io.github.skynet1748.mirai.economy

import io.github.skynet1748.mirai.economy.command.EconomyCoreCommand
import io.github.skynet1748.mirai.economy.command.EconomyCoreSimpleCommand
import io.github.skynet1748.mirai.economy.config.EconomyApiConfig
import io.github.skynet1748.mirai.economy.default.SimpleEconomyService
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.isRegistered
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.unregister
import net.mamoe.mirai.console.data.PluginData
import net.mamoe.mirai.console.plugin.id
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.utils.info
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
        reloadConfig()

        for (command in EconomyCoreCommand) {
            command.register()
        }
    }

    public fun reloadConfig() {
        EconomyApiConfig.reload()

        if (!EconomyApi.has(id) && EconomyApiConfig.registerSimpleEconomyService) {
            EconomyApi.register(id, SimpleEconomyService, false)
            if (!EconomyCoreSimpleCommand.isRegistered) EconomyCoreSimpleCommand.register()
        } else {
            if (EconomyApi.has(id)) EconomyApi.unregister(id)
            if (EconomyCoreSimpleCommand.isRegistered) EconomyCoreSimpleCommand.unregister()
        }
    }

    // 在 AbstractJvmPlugin 的 PluginData.reload() 无法被重载
    // 此为 mamoe/mirai#2088 临时解决方案
    @OptIn(ConsoleExperimentalApi::class)
    public fun loadData(data: PluginData) {
        File(dataFolder, data.saveName).parentFile?.mkdirs()
        loader.dataStorage.load(this, data)
    }
}