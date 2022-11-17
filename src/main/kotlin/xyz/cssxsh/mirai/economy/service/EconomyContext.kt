package xyz.cssxsh.mirai.economy.service

import net.mamoe.mirai.*
import net.mamoe.mirai.contact.*
import xyz.cssxsh.mirai.economy.*
import java.io.*
import kotlin.jvm.*

/**
 * 经济操作
 */
public interface EconomyAction {

    /**
     * 获取余额
     * @param currency 币种
     */
    @EconomyDsl
    @Throws(UnsupportedOperationException::class)
    public operator fun EconomyAccount.get(currency: EconomyCurrency): Double

    /**
     * 获取各个币种的余额
     */
    @EconomyDsl
    @Throws(UnsupportedOperationException::class)
    public fun EconomyAccount.balance(): Map<EconomyCurrency, Double>

    /**
     * 设置余额
     * @param currency 币种
     * @param quantity 金额
     */
    @EconomyDsl
    @Throws(UnsupportedOperationException::class)
    public operator fun EconomyAccount.set(currency: EconomyCurrency, quantity: Double)

    /**
     * 添加余额
     */
    @EconomyDsl
    @JvmSynthetic
    @Throws(UnsupportedOperationException::class)
    public operator fun EconomyAccount.plusAssign(value: Pair<EconomyCurrency, Double>) {
        return plusAssign(value.first, value.second)
    }

    /**
     * 添加余额
     * @param currency 币种
     * @param quantity 金额
     */
    @EconomyDsl
    @Throws(UnsupportedOperationException::class)
    public fun EconomyAccount.plusAssign(currency: EconomyCurrency, quantity: Double)

    /**
     * 减少余额
     */
    @EconomyDsl
    @JvmSynthetic
    @Throws(UnsupportedOperationException::class)
    public operator fun EconomyAccount.minusAssign(value: Pair<EconomyCurrency, Double>) {
        return minusAssign(value.first, value.second)
    }

    /**
     * 减少余额
     * @param currency 币种
     * @param quantity 金额
     */
    @EconomyDsl
    @Throws(UnsupportedOperationException::class)
    public fun EconomyAccount.minusAssign(currency: EconomyCurrency, quantity: Double)

    /**
     * 加倍余额
     */
    @EconomyDsl
    @JvmSynthetic
    @Throws(UnsupportedOperationException::class)
    public operator fun EconomyAccount.timesAssign(value: Pair<EconomyCurrency, Double>) {
        return timesAssign(value.first, value.second)
    }

    /**
     * 加倍余额
     * @param currency 币种
     * @param quantity 金额
     */
    @EconomyDsl
    @Throws(UnsupportedOperationException::class)
    public fun EconomyAccount.timesAssign(currency: EconomyCurrency, quantity: Double)

    /**
     * 减倍余额
     */
    @EconomyDsl
    @JvmSynthetic
    @Throws(UnsupportedOperationException::class)
    public operator fun EconomyAccount.divAssign(value: Pair<EconomyCurrency, Double>) {
        return divAssign(value.first, value.second)
    }

    /**
     * 减以余额
     * @param currency 币种
     * @param quantity 金额
     */
    @EconomyDsl
    @Throws(UnsupportedOperationException::class)
    public fun EconomyAccount.divAssign(currency: EconomyCurrency, quantity: Double)

    /**
     * 获取各个账户的余额
     * @since 1.0.1
     */
    @EconomyDsl
    @Throws(UnsupportedOperationException::class)
    public fun EconomyCurrency.balance(): Map<EconomyAccount, Double>

    /**
     * 执行复杂业务
     * @since 1.0.3
     */
    @EconomyDsl
    @Throws(UnsupportedOperationException::class)
    public fun EconomyCurrency.transaction(block: EconomyTransaction.() -> Unit)
}

/**
 * 经济上下文, 不同种类的上下文对应不同的场景
 * @property flush
 * @property close
 */
public sealed interface EconomyContext : Flushable, AutoCloseable, EconomyAction {
    /**
     * 上下文的ID，ID 相同的上下文应该是等价的
     */
    public val id: String

    /**
     * 上下文所属的插件服务
     */
    public val service: IEconomyService

    /**
     * 硬通货币
     */
    @EconomyDsl
    @get:Throws(UnsupportedOperationException::class)
    @set:Throws(UnsupportedOperationException::class)
    public var hard: EconomyCurrency
}

/**
 * 全局上下文
 */
public interface GlobalEconomyContext : EconomyContext

/**
 * BOT上下文
 */
public interface BotEconomyContext : EconomyContext {
    /**
     * 对应的 BOT
     */
    public val bot: Bot
}

/**
 * 群组上下文
 */
public interface GroupEconomyContext : EconomyContext {
    /**
     * 对应的群组
     */
    public val group: Group
}

/**
 * 上下文管理器
 */
public interface EconomyContextManager {

    /**
     * 全局上下文
     */
    public fun global(): GlobalEconomyContext

    /**
     * 机器人上下文
     */
    public fun context(target: Bot): BotEconomyContext

    /**
     * 群组上下文
     */
    public fun context(target: Group): GroupEconomyContext
}
