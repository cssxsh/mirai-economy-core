package xyz.cssxsh.mirai.economy.event

import net.mamoe.mirai.event.*
import xyz.cssxsh.mirai.economy.service.*

/**
 * 经济货币事务事件
 * @since 1.0.3
 */
public class EconomyCurrencyTransactionEvent(
    public override val service: IEconomyService,
    public val transaction: EconomyTransaction
) : EconomyEvent, AbstractEvent(), CancellableEvent {
    public val currency: EconomyCurrency get() = transaction.currency
}