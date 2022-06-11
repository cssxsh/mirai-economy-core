package io.github.skynet1748.mirai.economy

import net.mamoe.mirai.console.plugin.Plugin
import net.mamoe.mirai.console.plugin.id

public object EconomyAPI {
    /**
     * 已注册的经济接口
     * key 是插件名id，value 是接口实例
     */
    private val registeredEconomy = hashMapOf<String, IEconomyService>()
    private var defaultEconomy = MiraiEconomyCore.id;
    init {
        registeredEconomy[defaultEconomy] = SimpleEconomyService
    }
    @JvmOverloads
    public fun get(pluginId : String = defaultEconomy): IEconomyService? = registeredEconomy[pluginId]

    public fun register(pluginId: String, service: IEconomyService) {
        registeredEconomy[pluginId] = service
        defaultEconomy = pluginId
    }

    public fun has(pluginId: String): Boolean = registeredEconomy.containsKey(pluginId)
    public fun remove(pluginId: String) {
        registeredEconomy.remove(pluginId)
    }
}