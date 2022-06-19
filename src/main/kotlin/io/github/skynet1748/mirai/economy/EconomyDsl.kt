@file:JvmName(name = "EconomyUtils")

package io.github.skynet1748.mirai.economy

import io.github.skynet1748.mirai.economy.service.*
import net.mamoe.mirai.contact.*
import net.mamoe.mirai.event.events.*

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.TYPE)
@DslMarker
public annotation class EconomyDsl

public fun globalEconomy(): GlobalEconomyContext {
    return EconomyService.global
}

public fun <T> globalEconomy(block: GlobalEconomyContext.() -> T): T {
    return EconomyService.global.run(block)
}

public val User.economy: UserEconomyContext
    get() = EconomyService.context(target = this)

public fun <T> User.economy(block: UserEconomyContext.() -> T): T {
    return EconomyService.context(target = this).run(block)
}

public val Group.economy: GroupEconomyContext
    get() = EconomyService.context(target = this)

public fun <T> Group.economy(block: GroupEconomyContext.() ->  T): T {
    return EconomyService.context(target = this).run(block)
}

public val MessageEvent.economy: MessageEconomyContext
    get() = EconomyService.context(target = this)

public fun <T> MessageEvent.economy(block: MessageEconomyContext.() -> T): T {
    return EconomyService.context(target = this).run(block)
}