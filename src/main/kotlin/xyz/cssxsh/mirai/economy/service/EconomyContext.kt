package xyz.cssxsh.mirai.economy.service

import net.mamoe.mirai.*
import net.mamoe.mirai.contact.*
import xyz.cssxsh.mirai.economy.*
import kotlin.jvm.*

/**
 * 经济上下文, 不同种类的上下文对应不同的场景
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
     * 硬通货币
     */
    @get:Throws(UnsupportedOperationException::class)
    public val hard: EconomyCurrency

    // region Action

    /**
     * 获取余额
     */
    @EconomyDsl
    @Throws(UnsupportedOperationException::class)
    public operator fun EconomyAccount.get(currency: EconomyCurrency): Double

    @EconomyDsl
    @Throws(UnsupportedOperationException::class)
    public fun EconomyAccount.balance(): Map<EconomyCurrency, Double>

    /**
     * 设置余额
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
     */
    @EconomyDsl
    @Throws(UnsupportedOperationException::class)
    public fun EconomyAccount.plusAssign(currency: EconomyCurrency, quantity: Double)

    /**
     * 添加余额
     */
    @EconomyDsl
    @JvmSynthetic
    @Throws(UnsupportedOperationException::class)
    public operator fun EconomyAccount.minusAssign(value: Pair<EconomyCurrency, Double>) {
        return minusAssign(value.first, value.second)
    }

    /**
     * 添加余额
     */
    @EconomyDsl
    @Throws(UnsupportedOperationException::class)
    public fun EconomyAccount.minusAssign(currency: EconomyCurrency, quantity: Double)

    // endregion
}

public interface GlobalEconomyContext : EconomyContext

public interface BotEconomyContext : EconomyContext {
    public val bot: Bot
}

public interface GroupEconomyContext : EconomyContext {
    public val group: Group
}

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
