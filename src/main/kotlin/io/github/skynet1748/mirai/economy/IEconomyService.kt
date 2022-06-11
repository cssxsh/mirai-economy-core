package io.github.skynet1748.mirai.economy

import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.value
import java.io.File
import kotlin.streams.toList

public interface IEconomyService {
    public suspend fun getGlobalContext() : IEconomyContext
    public suspend fun getGroupContext(groupId: Long) : IEconomyContext
}
public interface IEconomyContext {
    public val service : IEconomyService

    /**
     * 创建账户
     */
    public suspend fun createAccount(userId: Long, money: Double = 0.0)

    /**
     * 查询是否有该账户
     */
    public suspend fun hasAccount(userId: Long) : Boolean

    /**
     * 列出账户列表
     * @param count 列出的数量，0为无限制
     */
    public suspend fun listAccounts(count : Int = 0) : List<Long>

    /**
     * 查询账户是否有足够的钱 (余额 >= money)
     */
    public suspend fun has(userId: Long, money: Double) : Boolean

    /**
     * 获取账户余额
     */
    public suspend fun get(userId: Long) : Double

    /**
     * 设置账户余额
     */
    public suspend fun set(userId: Long, money : Double)

    /**
     * 将钱存入账户
     */
    public suspend fun increase(userId: Long, money: Double) : Double

    /**
     * 从账户中取钱
     */
    public suspend fun decrease(userId: Long, money: Double) : Double
}
public interface IEconomyContextGlobal : IEconomyContext
public interface IEconomyContextGroup : IEconomyContext {
    public val groupId : Long
}

/**
 * 从文件中即时读写数据的默认经济实现
 */
public object SimpleEconomyService : IEconomyService {
    internal val globalData : SimpleData = SimpleData()
    private val groupData = mutableMapOf<Long, SimpleData>()
    init {
        MiraiEconomyCore.loadData(globalData)
    }
    internal fun getGroupData(groupId: Long) : SimpleData {
        if (groupData.containsKey(groupId)) return groupData[groupId]!!
        val data = SimpleData("groups/$groupId")
        MiraiEconomyCore.loadData(data)
        groupData[groupId] = data
        return data
    }
    override suspend fun getGlobalContext(): IEconomyContext = SimpleContextGlobal(this)

    override suspend fun getGroupContext(groupId: Long): IEconomyContext = SimpleContextGroup(this, groupId)
}
public class SimpleData(public val path: String = "global") : AutoSavePluginData("data/" + path) {
    public val users : MutableMap<Long, Double> by value()
}
public class SimpleContextGlobal(override val service: SimpleEconomyService) : IEconomyContextGlobal {

    override suspend fun createAccount(userId: Long, money: Double) {
        service.globalData.users[userId] = money
    }

    override suspend fun hasAccount(userId: Long): Boolean {
        return service.globalData.users.containsKey(userId)
    }

    override suspend fun listAccounts(count: Int): List<Long> {
        val result = listOf<Long>()
        if (count > 0) {
            var i = 0
            for (u in service.globalData.users.keys){
                result.plus(u)
                i++
                if (i >= count) break
            }
            return result
        }
        return service.globalData.users.keys.stream().toList()
    }

    override suspend fun has(userId: Long, money: Double): Boolean {
        return get(userId) >= money
    }

    override suspend fun get(userId: Long): Double {
        return service.globalData.users.getOrDefault(userId, 0.0)
    }

    override suspend fun set(userId: Long, money: Double) {
        service.globalData.users[userId] = money
    }

    override suspend fun increase(userId: Long, money: Double): Double {
        val m = get(userId) + money
        set(userId, m)
        return m
    }

    override suspend fun decrease(userId: Long, money: Double): Double {
        val m = get(userId) - money
        set(userId, m)
        return m
    }
}
public class SimpleContextGroup(override val service: SimpleEconomyService, public val groupId: Long) : IEconomyContextGlobal {
    private val data : SimpleData = service.getGroupData(groupId)
    override suspend fun createAccount(userId: Long, money: Double) {
        data.users[userId] = money
    }

    override suspend fun hasAccount(userId: Long): Boolean {
        return data.users.containsKey(userId)
    }

    override suspend fun listAccounts(count: Int): List<Long> {
        val result = listOf<Long>()
        if (count > 0) {
            var i = 0
            for (u in data.users.keys){
                result.plus(u)
                i++
                if (i >= count) break
            }
            return result
        }
        return data.users.keys.stream().toList()
    }

    override suspend fun has(userId: Long, money: Double): Boolean {
        return get(userId) >= money
    }

    override suspend fun get(userId: Long): Double {
        return data.users.getOrDefault(userId, 0.0)
    }

    override suspend fun set(userId: Long, money: Double) {
        data.users[userId] = money
    }

    override suspend fun increase(userId: Long, money: Double): Double {
        val m = get(userId) + money
        set(userId, m)
        return m
    }

    override suspend fun decrease(userId: Long, money: Double): Double {
        val m = get(userId) - money
        set(userId, m)
        return m
    }
}