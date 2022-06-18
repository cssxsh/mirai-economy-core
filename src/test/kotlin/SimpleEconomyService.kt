import io.github.skynet1748.mirai.economy.*
import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.value
import kotlin.streams.toList

/**
 * 从文件中即时读写数据的默认经济实现
 */
object SimpleEconomyService : IEconomyService {
    internal val globalData: SimpleData = SimpleData()
    private val groupData = mutableMapOf<Long, SimpleData>()

    init {
        MiraiEconomyCore.loadData(globalData)
    }

    internal fun getGroupData(groupId: Long): SimpleData {
        if (groupData.containsKey(groupId)) return groupData[groupId]!!
        val data = SimpleData("groups/$groupId")
        MiraiEconomyCore.loadData(data)
        groupData[groupId] = data
        return data
    }

    override fun getGlobalContext(): IEconomyContextGlobal = SimpleContextGlobal(this)

    override fun getGroupContext(groupId: Long): IEconomyContextGroup = SimpleContextGroup(this, groupId)
}

/**
 * 默认经济配置文件
 */
class SimpleData(path: String = "global") : AutoSavePluginData("data/$path") {
    val users: MutableMap<Long, Double> by value()
}

/**
 * 默认的全局经济上下文
 *
 * 配置文件将存在 /data/$MiraiEconomyCore.id/data/global.yml
 */
class SimpleContextGlobal(override val service: SimpleEconomyService) : IEconomyContextGlobal {
    override val name: String = "全局"
    override fun createAccount(userId: Long, money: Double) {
        service.globalData.users[userId] = money
    }

    override fun hasAccount(userId: Long): Boolean {
        return service.globalData.users.containsKey(userId)
    }

    override fun listAccounts(count: Int): List<Long> {
        val result = listOf<Long>()
        if (count > 0) {
            var i = 0
            for (u in service.globalData.users.keys) {
                result.plus(u)
                i++
                if (i >= count) break
            }
            return result
        }
        return service.globalData.users.keys.stream().toList()
    }

    override fun has(userId: Long, money: Double): Boolean {
        return get(userId) >= money
    }

    override fun get(userId: Long): Double {
        return service.globalData.users.getOrDefault(userId, 0.0)
    }

    override fun set(userId: Long, money: Double) {
        service.globalData.users[userId] = money
    }

    override fun increase(userId: Long, money: Double): Double {
        val m = get(userId) + money
        set(userId, m)
        return m
    }

    override fun decrease(userId: Long, money: Double): Double {
        val m = get(userId) - money
        set(userId, m)
        return m
    }
}

/**
 * 默认的群聊经济上下文
 *
 * 配置文件将存在 /data/$MiraiEconomyCore.id/data/groups/$groupId.yml
 */
class SimpleContextGroup(
    override val service: SimpleEconomyService,
    override val groupId: Long
) : IEconomyContextGroup {
    override val name: String = "群$groupId"
    private val data: SimpleData = service.getGroupData(groupId)

    override fun createAccount(userId: Long, money: Double) {
        data.users[userId] = money
    }

    override fun hasAccount(userId: Long): Boolean {
        return data.users.containsKey(userId)
    }

    override fun listAccounts(count: Int): List<Long> {
        val result = listOf<Long>()
        if (count > 0) {
            var i = 0
            for (u in data.users.keys) {
                result.plus(u)
                i++
                if (i >= count) break
            }
            return result
        }
        return data.users.keys.stream().toList()
    }

    override fun has(userId: Long, money: Double): Boolean {
        return get(userId) >= money
    }

    override fun get(userId: Long): Double {
        return data.users.getOrDefault(userId, 0.0)
    }

    override fun set(userId: Long, money: Double) {
        data.users[userId] = money
    }

    override fun increase(userId: Long, money: Double): Double {
        val m = get(userId) + money
        set(userId, m)
        return m
    }

    override fun decrease(userId: Long, money: Double): Double {
        val m = get(userId) - money
        set(userId, m)
        return m
    }
}