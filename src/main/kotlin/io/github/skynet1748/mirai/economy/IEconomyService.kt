@file:JvmBlockingBridge

package io.github.skynet1748.mirai.economy

import me.him188.kotlin.jvm.blocking.bridge.JvmBlockingBridge

/**
 * 经济服务，用于存取数据和获取经济上下文
 *
 * 默认实现请见 [默认经济服务][io.github.skynet1748.mirai.economy.default.SimpleEconomyService]
 */
public interface IEconomyService {
    public suspend fun getGlobalContext(): IEconomyContext
    public suspend fun getGroupContext(groupId: Long): IEconomyContext
}

/**
 * 经济上下文，用于区分当前环境分开存取用户数据
 */
public interface IEconomyContext {
    /**
     * 该上下文的显示名称
     */
    public val name: String
    public val service: IEconomyService

    /**
     * 创建账户
     */
    public suspend fun createAccount(userId: Long, money: Double = 0.0)

    /**
     * 查询是否有该账户
     */
    public suspend fun hasAccount(userId: Long): Boolean

    /**
     * 列出账户列表
     * @param count 列出的数量，0为无限制
     */
    public suspend fun listAccounts(count: Int = 0): List<Long>

    /**
     * 查询账户是否有足够的钱 (余额 >= money)
     */
    public suspend fun has(userId: Long, money: Double): Boolean

    /**
     * 获取账户余额
     */
    public suspend fun get(userId: Long): Double

    /**
     * 设置账户余额
     */
    public suspend fun set(userId: Long, money: Double)

    /**
     * 将钱存入账户
     */
    public suspend fun increase(userId: Long, money: Double): Double

    /**
     * 从账户中取钱
     */
    public suspend fun decrease(userId: Long, money: Double): Double
}

/**
 * 全局上下文，适用于好友等非群聊环境
 *
 * 默认实现请见 [默认全局上下文][io.github.skynet1748.mirai.economy.default.SimpleContextGlobal]
 */
public interface IEconomyContextGlobal : IEconomyContext

/**
 * 群聊上下文，适用于群聊环境
 *
 * 默认实现请见 [默认群聊上下文][io.github.skynet1748.mirai.economy.default.SimpleContextGroup]
 */
public interface IEconomyContextGroup : IEconomyContext {
    public val groupId: Long
}