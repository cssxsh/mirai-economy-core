package io.github.skynet1748.mirai.economy.service

import net.mamoe.mirai.contact.*
import net.mamoe.mirai.event.events.*
import net.mamoe.mirai.utils.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.jvm.Throws
import kotlin.reflect.full.createInstance

/**
 * 经济服务接口
 */
public interface IEconomyService {
    /**
     * 经济服务 ID
     */
    public val id: String

    /**
     * 全局上下文
     */
    public val global: IEconomyGlobalContext

    /**
     * 用户上下文
     */
    public fun user(target: User): IEconomyUserContext

    /**
     * 群组上下文
     */
    public fun group(target: Group): IEconomyGroupContext

    /**
     * 消息上下文
     */
    public fun message(target: MessageEvent): IEconomyMessageContext

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
         * @see NAME_KEY
         * @see loaders
         */
        @Throws(RuntimeException::class)
        public fun create(): IEconomyService {
            val name: String? = System.getProperty(NAME_KEY)

            for (loader in loaders) {
                for (provider in loader.stream()) {
                    val clazz = provider.type()

                    if (name != null) {
                        if (name != clazz.getAnnotation(EconomyServiceName::class.java)?.name) {
                            continue
                        }
                    }

                    try {
                        with(clazz.kotlin) { objectInstance ?: createInstance() }
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