package io.github.skynet1748.mirai.economy.service

import kotlin.jvm.*

/**
 * 货币
 */
public interface EconomyCurrency {
    public val name: String
    public val description: String

    /**
     * 格式化数量
     */
    public fun format(amount: Double): String
}

/**
 * 货币管理器
 */
public interface EconomyCurrencyManager {

    /**
     * 货币篮子, key 是货币名，value 是货币对象
     */
    public val basket: Map<String, EconomyCurrency>

    /**
     * 注册货币
     * @param currency 货币实例
     * @throws IllegalArgumentException 货币已存在
     */
    @Throws(IllegalArgumentException::class)
    public fun register(currency: EconomyCurrency, override: Boolean = false)
}