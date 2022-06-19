package io.github.skynet1748.mirai.economy.service

import net.mamoe.mirai.contact.*
import net.mamoe.mirai.event.events.*
import net.mamoe.mirai.utils.*

/**
 * 经济上下文接口
 */
public sealed interface EconomyContext {
    /**
     * 上下文的ID，ID 相同的上下文应该是等价的
     */
    public val id: String

    /**
     * 上下文所属的插件服务
     */
    public val service: IEconomyService

    /**
     * 空账户
     */
    public val empty: EmptyEconomyAccount

    /**
     * 获取联系人的经济账户
     */
    public fun Contact.account(): EconomyAccount

    /**
     * 获取余额
     */
    public operator fun EconomyAccount.get(currency: String): Double

    /**
     * 获取余额
     */
    public operator fun Contact.get(currency: String): Double

    /**
     * 设置余额
     */
    public operator fun EconomyAccount.set(currency: String, quantity: Double)

    /**
     * 设置余额
     */
    public operator fun Contact.set(currency: String, quantity: Double)

    /**
     * 转账
     */
    public fun transfer(from: EconomyAccount, to: EconomyAccount, currency: String, quantity: Double)
}

public abstract class AbstractEconomyContext : EconomyContext {
    protected open val logger: MiraiLogger by lazy { MiraiLogger.Factory.create(this::class.java, identity = id) }
}

public interface GlobalEconomyContext : EconomyContext

public interface ContactEconomyContext : EconomyContext {
    public val subject: Contact
}

public interface UserEconomyContext : ContactEconomyContext {
    public val user: User
    override val subject: Contact get() = user
}

public interface GroupEconomyContext : ContactEconomyContext {
    public val group: Group
    override val subject: Contact get() = group
}

public interface MessageEconomyContext : UserEconomyContext {
    public val event: MessageEvent
    public override val user: User get() = event.sender
    public override val subject: Contact get() = event.subject
}
