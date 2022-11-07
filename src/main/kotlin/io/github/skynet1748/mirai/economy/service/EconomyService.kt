package io.github.skynet1748.mirai.economy.service

import net.mamoe.mirai.utils.*
import java.util.*
import kotlin.collections.*
import kotlin.jvm.*

/**
 * 经济服务接口
 *
 * @see AbstractEconomyService
 */
public interface IEconomyService : EconomyContextManager, EconomyAccountManager, EconomyCurrencyManager, Closeable {
    /**
     * 经济服务 ID
     */
    public val id: String

    /**
     * 重载数据
     */
    @Throws(java.io.IOException::class)
    public fun reload()

    /**
     * 关闭
     */
    @Throws(java.io.IOException::class)
    public override fun close()

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
        public fun create(name: String?): IEconomyService {
            for (loader in loaders) {
                for (provider in loader.stream()) {
                    val clazz = provider.type()

                    if (name != null) {
                        if (name != clazz.getAnnotation(EconomyServiceName::class.java)?.name) {
                            continue
                        }
                    }

                    try {
                        provider.get()
                    } catch (cause: ServiceConfigurationError) {
                        logger.warning({ "创建 ${clazz.name} 服务失败" }, cause)
                    }
                }
            }

            throw RuntimeException("无法创建 EconomyService")
        }
    }
}

/**
 * EconomyService 抽象实现
 * 实现 [IEconomyService] 时应继承此类
 * @see IEconomyService
 */
public abstract class AbstractEconomyService : IEconomyService {
    protected open val logger: MiraiLogger by lazy { MiraiLogger.Factory.create(this::class.java, identity = id) }
}


/**
 * 经济服务名注解，用于标记一个服务的名称, 用于在服务初始化时匹配服务
 * @param name 服务的名称
 * @see IEconomyService.create
 */
@Target(AnnotationTarget.CLASS)
public annotation class EconomyServiceName(
    val name: String
)