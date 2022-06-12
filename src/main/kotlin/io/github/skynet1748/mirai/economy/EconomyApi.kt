package io.github.skynet1748.mirai.economy

import io.github.skynet1748.mirai.economy.config.EconomyApiConfig

public object EconomyApi {
    /**
     * 已注册的经济接口
     * key 是插件名id，value 是接口实例
     */
    private val registeredEconomy = hashMapOf<String, IEconomyService>()

    /**
     * 获取已注册的所有经济服务插件ID
     */
    public val services: Set<String>
        get() = registeredEconomy.keys

    /**
     * 获取已注册的经济服务
     */
    @JvmOverloads
    public operator fun get(pluginId: String = EconomyApiConfig.defaultEconomyService): IEconomyService? =
        registeredEconomy[pluginId]

    /**
     * 注册经济服务
     * @param pluginId 插件ID
     * @param service 经济服务实例
     * @param setToDefault 是否将该经济服务设为默认值
     */
    @JvmOverloads
    public fun register(pluginId: String, service: IEconomyService, setToDefault: Boolean = true) {
        if (registeredEconomy.containsKey(pluginId)) {
            MiraiEconomyCore.logger.warning("Economy service `$pluginId` has been registered.")
            return
        }
        registeredEconomy[pluginId] = service
        if (setToDefault) EconomyApiConfig.defaultEconomyService = pluginId
        MiraiEconomyCore.logger.info("Economy service `$pluginId` registered successfully.")
    }

    /**
     * 获取经济服务是否已注册
     * @param pluginId 插件ID
     */
    public fun has(pluginId: String): Boolean = registeredEconomy.containsKey(pluginId)

    /**
     * 注销经济服务
     * @param pluginId 插件ID
     */
    public fun unregister(pluginId: String) {
        registeredEconomy.remove(pluginId)
    }
}