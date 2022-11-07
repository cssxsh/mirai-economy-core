@file:JvmName(name = "EconomyUtils")

package xyz.cssxsh.mirai.economy

import net.mamoe.mirai.*
import net.mamoe.mirai.contact.*
import net.mamoe.mirai.event.*
import net.mamoe.mirai.event.events.*
import xyz.cssxsh.mirai.economy.service.*

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.TYPE, AnnotationTarget.PROPERTY)
@DslMarker
public annotation class EconomyDsl

@EconomyDsl
@JvmName("getGlobalEconomy")
public fun globalEconomy(): GlobalEconomyContext {
    return EconomyService.global()
}

@EconomyDsl
@JvmName("runGlobalEconomy")
public fun <T> globalEconomy(block: GlobalEconomyContext.() -> T): T {
    return globalEconomy().run(block)
}

@EconomyDsl
@JvmName("getEconomy")
public fun Bot.economy(): BotEconomyContext {
    return EconomyService.context(target = this)
}

@EconomyDsl
@JvmName("runEconomy")
public fun <T> Bot.economy(block: BotEconomyContext.() -> T): T {
    return economy().run(block)
}

@EconomyDsl
@JvmName("getEconomy")
public fun Group.economy(): GroupEconomyContext {
    return EconomyService.context(target = this)
}

@EconomyDsl
@JvmName("runEconomy")
public fun <T> Group.economy(block: GroupEconomyContext.() -> T): T {
    return economy().run(block)
}

@EconomyDsl
@JvmName("getEconomy")
public fun Contact.economy(): EconomyContext {
    return when (this) {
        is Group -> economy()
        else -> bot.economy()
    }
}

@EconomyDsl
@JvmName("runEconomy")
public fun <T> Contact.economy(block: EconomyContext.() -> T): T {
    return economy().run(block)
}

@EconomyDsl
@JvmName("getEconomy")
public fun Event.economy(): EconomyContext {
    return when (this) {
        is GroupEvent -> group.economy()
        is BotEvent -> bot.economy()
        else -> globalEconomy()
    }
}

@EconomyDsl
@JvmName("runEconomy")
public fun <T> Event.economy(block: EconomyContext.() -> T): T {
    return economy().run(block)
}