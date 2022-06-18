package io.github.skynet1748.mirai.economy.service

import io.github.skynet1748.mirai.economy.EconomyDsl
import kotlinx.coroutines.sync.Mutex
import net.mamoe.mirai.contact.*
import net.mamoe.mirai.event.events.*
import net.mamoe.mirai.utils.*

public interface IEconomyContext {
    public val id: String
    public val service: IEconomyService

    @EconomyDsl
    public fun set(target: Any, coin: String, quantity: Double)

    @EconomyDsl
    public fun transfer(from: Any, to: Any, coin: String, quantity: Double)
}

public abstract class AbstractEconomyContext : IEconomyContext {
    protected open val logger: MiraiLogger by lazy { MiraiLogger.Factory.create(this::class.java, identity = id) }
    protected val mutex: Mutex = Mutex()
}

public interface IEconomyGlobalContext : IEconomyContext

public interface IEconomyContactContext : IEconomyContext {
    public val subject: Contact
}

public interface IEconomyUserContext : IEconomyContactContext {
    public val user: User
    override val subject: Contact get() = user
    public val current: Any
}

public interface IEconomyGroupContext : IEconomyContactContext {
    public val group: Group
    override val subject: Contact get() = group
}

public interface IEconomyMessageContext : IEconomyUserContext {
    public val event: MessageEvent
    public override val user: User get() = event.sender
    public override val subject: Contact get() = event.subject
}
