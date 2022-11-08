package xyz.cssxsh.mirai.economy.console

import net.mamoe.mirai.event.*
import xyz.cssxsh.mirai.economy.event.*

@PublishedApi
internal object MiraiEconomyListenerHost : SimpleListenerHost() {
    @EventHandler
    fun EconomyCurrencyRegisteredEvent.handle() {
        //
    }

    @EventHandler
    fun EconomyBalanceChangeEvent.handle() {
        //
    }
}