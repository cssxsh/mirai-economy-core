package xyz.cssxsh.mirai.economy.event

import net.mamoe.mirai.event.*
import xyz.cssxsh.mirai.economy.service.*

/**
 * 经济货币被注册事件
 * @property cancel 阻止注册
 */
public class EconomyCurrencyRegisteredEvent(
    override val currency: EconomyCurrency,
    override val service: IEconomyService
) : EconomyCurrencyEvent, AbstractEvent(), CancellableEvent