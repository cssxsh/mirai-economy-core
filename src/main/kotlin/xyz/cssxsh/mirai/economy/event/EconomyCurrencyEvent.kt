package xyz.cssxsh.mirai.economy.event;

import xyz.cssxsh.mirai.economy.service.*

/**
 * 经济货币相关事件
 */
public interface EconomyCurrencyEvent : EconomyEvent {
    /**
     * 对应经济货币
     */
    public val currency: EconomyCurrency
}