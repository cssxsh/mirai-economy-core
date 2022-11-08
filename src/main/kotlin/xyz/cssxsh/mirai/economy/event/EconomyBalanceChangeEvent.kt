package xyz.cssxsh.mirai.economy.event

import net.mamoe.mirai.event.*
import xyz.cssxsh.mirai.economy.service.*

/**
 * 余额事件
 * @param current 当前金额（未改变前）
 * @param change 变动金额
 * @param mode 变动类型
 * @property cancel 阻止注册
 */
public class EconomyBalanceChangeEvent(
    override val account: EconomyAccount,
    override val service: IEconomyService,
    override val currency: EconomyCurrency,
    public val current: Double,
    public val change: Double,
    public val mode: EconomyBalanceChangeMode
) : EconomyAccountEvent, EconomyCurrencyEvent, AbstractEvent(), CancellableEvent