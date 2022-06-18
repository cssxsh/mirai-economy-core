import io.github.skynet1748.mirai.economy.EconomyApi
import io.github.skynet1748.mirai.economy.MiraiEconomyCore
import net.mamoe.mirai.console.command.*
import okhttp3.internal.toLongOrDefault

object EconomyCoreSimpleCommand : CompositeCommand(
    owner = MiraiEconomyCore,
    "economy",
    description = "默认经济实现 (自动确定上下文)"
), Command {
    @SubCommand
    @Description("获取用户金钱 (控制台,全局上下文)")
    suspend fun ConsoleCommandSender.get(id: Long) {
        val context = EconomyApi.get()!!.getGlobalContext()
        sendMessage("[" + context.name + "] $id 的金钱数量: " + context.get(id))
    }

    @SubCommand
    @Description("获取用户金钱 (自动获取上下文)")
    suspend fun UserCommandSender.get(id: Long = this.user.id) {
        val context = if (this is MemberCommandSender)
            EconomyApi.get()!!.getGroupContext(this.group.id)
        else EconomyApi.get()!!.getGlobalContext()
        sendMessage("[" + context.name + "] $id 的金钱数量: " + context.get(id))
    }

    @SubCommand
    @Description("设置用户金钱 (控制台,全局上下文)")
    suspend fun ConsoleCommandSender.set(id: Long, money: Double) {
        val context = EconomyApi.get()!!.getGlobalContext()
        context.set(id, money)
        sendMessage("[" + context.name + "] 已设置 $id 的金钱数量为: $money")
    }

    @SubCommand
    @Description("设置用户金钱 (自动获取上下文)")
    suspend fun UserCommandSender.set(id: Long = this.user.id, money: Double) {
        val context = if (this is MemberCommandSender)
            EconomyApi.get()!!.getGroupContext(this.group.id)
        else EconomyApi.get()!!.getGlobalContext()
        context.set(id, money)
        sendMessage("[" + context.name + "] 已设置 $id 的金钱数量为: $money")
    }
}

object EconomyCoreSimpleCommandWithContext : CompositeCommand(
    owner = MiraiEconomyCore,
    "/economy",
    description = "默认经济实现"
), Command {
    @SubCommand
    @Description("获取用户金钱")
    suspend fun CommandSender.get(ecoContext: String, id: Long) {
        val group = ecoContext.toLongOrDefault(0)
        val context = if (group > 0)
            EconomyApi.get()!!.getGroupContext(group)
        else EconomyApi.get()!!.getGlobalContext()
        sendMessage("[" + context.name + "] $id 的金钱数量: " + context.get(id))
    }

    @SubCommand
    @Description("设置用户金钱")
    suspend fun CommandSender.set(ecoContext: String, id: Long, money: Double) {
        val group = ecoContext.toLongOrDefault(0)
        val context = if (group > 0)
            EconomyApi.get()!!.getGroupContext(group)
        else EconomyApi.get()!!.getGlobalContext()
        context.set(id, money)
        sendMessage("[" + context.name + "] 已设置 $id 的金钱数量为: $money")
    }
}