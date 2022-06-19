package io.github.skynet1748.mirai.economy.service

import net.mamoe.mirai.contact.*
import net.mamoe.mirai.event.events.*
import net.mamoe.mirai.utils.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.jvm.Throws

/**
 * 经济服务接口
 *
 * @see AbstractEconomyService
 */
public interface IEconomyService {
    /**
     * 经济服务 ID
     */
    public val id: String

    /**
     * 全局上下文
     */
    public val global: GlobalEconomyContext

    /**
     * 用户上下文
     */
    public fun context(target: User): UserEconomyContext

    /**
     * 群组上下文
     */
    public fun context(target: Group): GroupEconomyContext

    /**
     * 消息上下文
     */
    public fun context(target: MessageEvent): MessageEconomyContext

    /**
     * 支持的货币, key 是货币名，value 是货币简介
     */
    public val basket: Map<String, String>

    /**
     * 注册货币
     * @param name 货币名
     * @param description 货币简介
     * @throws IllegalArgumentException 货币名已经被使用，或货币名不合法
     */
    @Throws(IllegalArgumentException::class)
    public fun currency(name: String, description: String)

    public companion object Factory {
        private val logger: MiraiLogger = MiraiLogger.Factory.create(Factory::class.java)
        internal const val NAME_KEY: String = "io.github.skynet1748.mirai.economy.service"

        /**
         * [IEconomyService] 加载器
         */
        internal val loaders: MutableList<ServiceLoader<IEconomyService>> = ArrayList()

        init {
            loaders.add(ServiceLoader.load(IEconomyService::class.java))
        }

        /**
         * 创建经纪服务实例
         * @param name 指定的服务名称
         * @throws RuntimeException 无法实例化插件服务
         * @see NAME_KEY System Property Key，可以用于指定服务
         * @see loaders 可用的服务加载器示例
         */
        @Throws(RuntimeException::class)
        @JvmOverloads
        public fun create(name: String? = System.getProperty(NAME_KEY)): IEconomyService {
            for (loader in loaders) {
                for (provider in loader.stream()) {
                    val clazz = provider.type()

                    if (name != null) {
                        if (name != clazz.getAnnotation(EconomyServiceName::class.java)?.name) {
                            continue
                        }
                    }

                    try {
                        clazz.kotlin.objectInstance ?: provider.get()
                    } catch (cause: Throwable) {
                        logger.warning({ "创建 ${clazz.name} 服务失败" }, cause)
                    }
                }
            }

            throw RuntimeException("无法创建 EconomyService")
        }
    }
}

public abstract class AbstractEconomyService : IEconomyService {
    protected open val logger: MiraiLogger by lazy { MiraiLogger.Factory.create(this::class.java, identity = id) }
}