package io.github.skynet1748.mirai.economy.command

import io.github.skynet1748.mirai.economy.MiraiEconomyCore
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand

public object EconomyCoreApiCommand : CompositeCommand(
    owner = MiraiEconomyCore,
    "economycore",
    description = "PIXIV信息指令"
), EconomyCoreCommand {
    @SubCommand
    @Description("重载配置文件")
    public suspend fun CommandSender.reload() {
        MiraiEconomyCore.reloadConfig()
        sendMessage("配置文件已重载")
    }
}