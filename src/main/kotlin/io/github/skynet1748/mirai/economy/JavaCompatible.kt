package io.github.skynet1748.mirai.economy

/**
 * [经济服务][io.github.skynet1748.mirai.economy.IEconomyService]的 Java 兼容
 */
public interface IJEconomyService : IEconomyService {
    public override suspend fun getGlobalContext() : IEconomyContext {
        return getGlobalContextJ()
    }
    public override suspend fun getGroupContext(groupId: Long) : IEconomyContext {
        return getGroupContextJ(groupId)
    }
    public fun getGlobalContextJ() : IEconomyContext
    public fun getGroupContextJ(groupId: Long) : IEconomyContext
}

/**
 * [经济上下文][io.github.skynet1748.mirai.economy.IEconomyContext]的 Java 兼容
 */
public interface IJEconomyContext : IEconomyContext {
    
    public override suspend fun createAccount(userId: Long, money: Double) {
        createAccountJ(userId, money)
    }
    
    public override suspend fun hasAccount(userId: Long): Boolean {
        return hasAccountJ(userId)
    }
    
    public override suspend fun listAccounts(count: Int): List<Long> {
        return listAccountsJ(count)
    }
    
    public override suspend fun has(userId: Long, money: Double): Boolean{
        return hasJ(userId, money)
    }
    
    public override suspend fun get(userId: Long): Double{
        return getJ(userId)
    }
    
    public override suspend fun set(userId: Long, money: Double)
    {
        setJ(userId, money)
    }
    
    public override suspend fun increase(userId: Long, money: Double): Double{
        return increaseJ(userId, money)
    }
    
    public override suspend fun decrease(userId: Long, money: Double): Double{
        return decreaseJ(userId, money)
    }

    /**
     * 创建账户
     */
    public fun createAccountJ(userId: Long, money: Double)
    /**
     * 查询是否有该账户
     */
    public fun hasAccountJ(userId: Long) : Boolean
    /**
     * 列出账户列表
     * @param count 列出的数量，0为无限制
     */
    public fun listAccountsJ(count: Int = 0): List<Long>
    /**
     * 查询账户是否有足够的钱 (余额 >= money)
     */
    public fun hasJ(userId: Long, money: Double): Boolean
    /**
     * 获取账户余额
     */
    public fun getJ(userId: Long): Double
    /**
     * 设置账户余额
     */
    public fun setJ(userId: Long, money: Double)
    /**
     * 将钱存入账户
     */
    public fun increaseJ(userId: Long, money: Double): Double
    /**
     * 从账户中取钱
     */
    public fun decreaseJ(userId: Long, money: Double): Double
}
