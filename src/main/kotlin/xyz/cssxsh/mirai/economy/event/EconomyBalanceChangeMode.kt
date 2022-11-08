package xyz.cssxsh.mirai.economy.event

import kotlinx.serialization.*

/**
 * 余额变动类型
 */
@Serializable
public enum class EconomyBalanceChangeMode {
    /**
     * 被设置
     */
    SET,

    /**
     * 被增加
     */
    PLUS,

    /**
     * 被减少
     */
    MINUS,

    /**
     * 被乘
     */
    TIMES,

    /**
     * 被除
     */
    DIV
}