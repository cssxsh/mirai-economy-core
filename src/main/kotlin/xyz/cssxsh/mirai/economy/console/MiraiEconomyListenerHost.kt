package xyz.cssxsh.mirai.economy.console

import kotlinx.coroutines.*
import net.mamoe.mirai.console.events.*
import net.mamoe.mirai.event.*
import net.mamoe.mirai.utils.*
import xyz.cssxsh.mirai.economy.event.*
import kotlin.coroutines.*

@PublishedApi
internal object MiraiEconomyListenerHost : SimpleListenerHost() {

    private val logger = MiraiLogger.Factory.create(this::class, identity = "mirai-economy")

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        when (exception) {
            is ExceptionInEventHandlerException -> {
                logger.warning({ "Exception in MiraiEconomyListenerHost" }, exception.cause)
            }
            else -> {
                logger.warning({ "Exception in MiraiEconomyListenerHost" }, exception)
            }
        }
    }

    @EventHandler
    internal fun ConsoleEvent.handle() {
        if (this !is StartupEvent) return
        launch {
            // XXX
            while (true) {
                val url = System.getProperty("xyz.cssxsh.mirai.economy.jpa")
                if (url == null) {
                    delay(timeMillis = 1_000)
                    continue
                }
                logger.info { "url $url" }
                break
            }
        }
    }

    @EventHandler
    fun EconomyCurrencyRegisteredEvent.handle() {
        //
    }

    @EventHandler
    fun EconomyBalanceChangeEvent.handle() {
        //
    }
}