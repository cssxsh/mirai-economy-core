import io.github.skynet1748.mirai.economy.EconomyApi
import io.github.skynet1748.mirai.economy.MiraiEconomyCore
import net.mamoe.mirai.console.MiraiConsole
import net.mamoe.mirai.console.terminal.MiraiConsoleImplementationTerminal
import net.mamoe.mirai.console.terminal.MiraiConsoleTerminalLoader
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import java.io.File

@OptIn(ConsoleExperimentalApi::class)
suspend fun main() {
    val instance = MiraiConsoleImplementationTerminal(
        rootPath = File("run").toPath()
    )
    MiraiConsoleTerminalLoader.startAsDaemon(instance)

    MiraiConsole.pluginManager.loadPlugin(MiraiEconomyCore)
    MiraiConsole.pluginManager.enablePlugin(MiraiEconomyCore)

    // 注册测试用经济服务 (java)
    EconomyApi.register("java", JavaEconomyService())

    JavaTest.main()

    MiraiConsole.job.join()
}

