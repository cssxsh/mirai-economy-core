@file:JvmName(name = "EconomyUtils")

package xyz.cssxsh.mirai.economy

import net.mamoe.mirai.*
import net.mamoe.mirai.contact.*
import net.mamoe.mirai.event.*
import net.mamoe.mirai.event.events.*
import xyz.cssxsh.mirai.economy.service.*

/**
 * 用于标记 Economy Dsl Api
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.TYPE, AnnotationTarget.PROPERTY)
@DslMarker
public annotation class EconomyDsl

/**
 * 获取全局经济上下文
 * @see GlobalEconomyContext
 */
@EconomyDsl
@JvmName("getGlobalEconomy")
public fun globalEconomy(): GlobalEconomyContext {
    return EconomyService.global()
}

/**
 * 在全局经济上下文中运行
 * @see GlobalEconomyContext
 */
@EconomyDsl
@JvmName("runGlobalEconomy")
public fun <T> globalEconomy(block: GlobalEconomyContext.() -> T): T {
    return globalEconomy().run(block)
}

/**
 * 获取Bot经济上下文
 * @see BotEconomyContext
 */
@EconomyDsl
@JvmName("getEconomy")
public fun Bot.economy(): BotEconomyContext {
    return EconomyService.context(target = this)
}

/**
 * 在Bot经济上下文中运行
 * @see BotEconomyContext
 */
@EconomyDsl
@JvmName("runEconomy")
public fun <T> Bot.economy(block: BotEconomyContext.() -> T): T {
    return economy().run(block)
}

/**
 * 获取Group经济上下文
 * @see GroupEconomyContext
 */
@EconomyDsl
@JvmName("getEconomy")
public fun Group.economy(): GroupEconomyContext {
    return EconomyService.context(target = this)
}

/**
 * 在Group经济上下文中运行
 * @see GroupEconomyContext
 */
@EconomyDsl
@JvmName("runEconomy")
public fun <T> Group.economy(block: GroupEconomyContext.() -> T): T {
    return economy().run(block)
}

/**
 * 获取经济上下文
 * @see GroupEconomyContext
 * @see BotEconomyContext
 */
@EconomyDsl
@JvmName("getEconomy")
public fun Contact.economy(): EconomyContext {
    return when (this) {
        is Group -> economy()
        else -> bot.economy()
    }
}

/**
 * 在经济上下文中运行
 * @see GroupEconomyContext
 * @see BotEconomyContext
 */
@EconomyDsl
@JvmName("runEconomy")
public fun <T> Contact.economy(block: EconomyContext.() -> T): T {
    return economy().run(block)
}

/**
 * 获取经济上下文
 * @see GroupEconomyContext
 * @see BotEconomyContext
 * @see GlobalEconomyContext
 */
@EconomyDsl
@JvmName("getEconomy")
public fun Event.economy(): EconomyContext {
    return when (this) {
        is GroupEvent -> group.economy()
        is BotEvent -> bot.economy()
        else -> globalEconomy()
    }
}

/**
 * 在经济上下文中运行
 * @see GroupEconomyContext
 * @see BotEconomyContext
 * @see GlobalEconomyContext
 */
@EconomyDsl
@JvmName("runEconomy")
public fun <T> Event.economy(block: EconomyContext.() -> T): T {
    return economy().run(block)
}