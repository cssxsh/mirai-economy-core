package xyz.cssxsh.mirai.economy

import net.mamoe.mirai.event.*
import net.mamoe.mirai.event.events.*
import xyz.cssxsh.mirai.economy.event.*

internal object EconomyServiceTestListenerHost : SimpleListenerHost() {

    @EventHandler
    fun EconomyServiceInitEvent.handle() {
        println("${service.id} 经济服务已初始化")
    }

    @EventHandler
    fun EconomyCurrencyRegisteredEvent.handle() {
        if (currency.id == EconomyServiceLaoLittleCoin.id) {
            println("发现变态 ${currency.name}")
            // 阻止注册
            cancel()
            return
        }
        println("${currency.name} 已注册")
    }


    @EventHandler
    fun EconomyBalanceChangeEvent.handle() {
        account
        service
        currency
        current
        change
        mode
        when (mode) {
            EconomyBalanceChangeMode.SET -> println("${account.uuid}#${currency.id} 余额已改变 ${current}->${change}")
            EconomyBalanceChangeMode.PLUS -> println("${account.uuid}#${currency.id} 余额增加 ${current}+${change}")
            EconomyBalanceChangeMode.MINUS -> println("${account.uuid}#${currency.id} 余额减少 ${current}-${change}")
            EconomyBalanceChangeMode.TIMES -> println("${account.uuid}#${currency.id} 余额翻倍 ${current}*${change}")
            EconomyBalanceChangeMode.DIV -> println("${account.uuid}#${currency.id} 余额减倍 ${current}/${change}")
        }
    }

    @EventHandler
    fun BotEvent.handle() {
        bot.economy {
            // ...
        }
    }

    @EventHandler
    fun GroupEvent.handle() {
        group.economy {
            // ...
        }
    }
}