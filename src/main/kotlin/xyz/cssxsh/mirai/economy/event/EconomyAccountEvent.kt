package xyz.cssxsh.mirai.economy.event;

import xyz.cssxsh.mirai.economy.service.*

/**
 * 经济账户相关事件
 */
public interface EconomyAccountEvent : EconomyEvent {
    /**
     * 对应的经济账户
     */
    public val account: EconomyAccount
}