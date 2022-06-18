package io.github.skynet1748.mirai.economy

import io.github.skynet1748.mirai.economy.config.EconomyApiConfig
import io.github.skynet1748.mirai.economy.events.EconomyApiRegisterEvent
import io.github.skynet1748.mirai.economy.events.EconomyApiUnRegisterEvent
import kotlinx.coroutines.launch
import me.him188.kotlin.jvm.blocking.bridge.JvmBlockingBridge
import net.mamoe.mirai.event.broadcast

public object EconomyApi {
    /**
     * 已注册的经济接口
     * key 是插件名id，value 是接口实例
     */
    private val registeredEconomy = hashMapOf<String, IEconomyService>()

    /**
     * 获取已注册的所有经济服务插件ID
     */
    @JvmStatic
    public val services: Set<String>
        get() = registeredEconomy.keys

    /**
     * 获取已注册的经济服务
     */
    @JvmStatic
    @JvmOverloads
    public operator fun get(pluginId: String = EconomyApiConfig.defaultEconomyService): IEconomyService? =
        registeredEconomy[pluginId]

    /**
     * 注册经济服务
     * @param pluginId 插件ID
     * @param service 经济服务实例
     * @param setToDefault 是否将该经济服务设为默认值
     *
     */
    @JvmStatic
    @JvmOverloads
    public fun register(pluginId: String, service: IEconomyService, setToDefault: Boolean = true) {
        if (registeredEconomy.containsKey(pluginId)) {
            MiraiEconomyCore.logger.warning("Economy service `$pluginId` has been registered.")
            return
        }
        MiraiEconomyCore.launch {
            val event = EconomyApiRegisterEvent(pluginId, service, setToDefault).broadcast()
            if(event.isCancelled) return@launch
            registeredEconomy[event.pluginId] = event.service
            if (event.isSetToDefault) EconomyApiConfig.defaultEconomyService = event.pluginId
            MiraiEconomyCore.logger.info("Economy service `${event.pluginId}` registered successfully.")
        }
    }

    /**
     * 获取经济服务是否已注册
     * @param pluginId 插件ID
     */
    @JvmStatic
    public fun has(pluginId: String): Boolean = registeredEconomy.containsKey(pluginId)

    /**
     * 注销经济服务
     * @param pluginId 插件ID
     *
     * @return 是否注销成功，失败原因可能是经济服务不存在或者插件拦截
     */
    @JvmStatic
    public fun unregister(pluginId: String) {
        val service = get(pluginId) ?: return
        MiraiEconomyCore.launch {
            val event = EconomyApiUnRegisterEvent(pluginId, service).broadcast()
            if (event.isCancelled) return@launch
            registeredEconomy.remove(pluginId)
        }
    }
}