package io.github.skynet1748.mirai.economy

import io.github.skynet1748.mirai.economy.service.*
import net.mamoe.mirai.contact.*
import net.mamoe.mirai.event.events.*

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.TYPE)
@DslMarker
public annotation class EconomyDsl

public val User.economy: IEconomyUserContext
    get() = EconomyService.user(target = this)

public fun <T> User.economy(block: IEconomyUserContext.() -> T): T {
    return EconomyService.user(target = this).run(block)
}

public val Group.economy: IEconomyGroupContext
    get() = EconomyService.group(target = this)

public fun <T> Group.economy(block: IEconomyGroupContext.() ->  T): T {
    return EconomyService.group(target = this).run(block)
}

public val MessageEvent.economy: IEconomyMessageContext
    get() = EconomyService.message(target = this)

public fun <T> MessageEvent.economy(block: IEconomyMessageContext.() -> T): T {
    return EconomyService.message(target = this).run(block)
}