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
 * 使用全局经济上下文
 * @see GlobalEconomyContext
 */
@EconomyDsl
@JvmName("useGlobalEconomy")
public fun <T> globalEconomy(block: GlobalEconomyContext.() -> T): T {
    return globalEconomy().use(block)
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
 * 使用Bot经济上下文中
 * @see BotEconomyContext
 */
@EconomyDsl
@JvmName("useEconomy")
public fun <T> Bot.economy(block: BotEconomyContext.() -> T): T {
    return economy().use(block)
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
 * 使用Group经济上下文
 * @see GroupEconomyContext
 */
@EconomyDsl
@JvmName("useEconomy")
public fun <T> Group.economy(block: GroupEconomyContext.() -> T): T {
    return economy().use(block)
}