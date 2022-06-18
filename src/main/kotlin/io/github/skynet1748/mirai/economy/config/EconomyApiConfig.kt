package io.github.skynet1748.mirai.economy.config

import io.github.skynet1748.mirai.economy.MiraiEconomyCore
import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.value
import net.mamoe.mirai.console.plugin.id
import net.mamoe.yamlkt.Comment

public object EconomyApiConfig : AutoSavePluginConfig("config") {
    @Comment("设置默认经济服务")
    public var defaultEconomyService: String by value(MiraiEconomyCore.id)
}