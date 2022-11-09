package xyz.cssxsh.mirai.economy.service

import kotlinx.coroutines.*
import net.mamoe.mirai.event.*
import net.mamoe.mirai.utils.*
import xyz.cssxsh.mirai.economy.event.*
import java.io.Flushable
import java.nio.file.*
import java.util.*
import kotlin.collections.*
import kotlin.jvm.*
import kotlin.reflect.*

/**
 * 经济服务接口
 *
 * @see AbstractEconomyService
 */
public interface IEconomyService : EconomyContextManager, EconomyAccountManager, EconomyCurrencyManager,
    Flushable, AutoCloseable {
    /**
     * 经济服务 ID
     */
    public val id: String

    /**
     * 重载数据
     */
    @Throws(java.io.IOException::class)
    public fun reload(folder: Path)

    /**
     * 经济服务构建工厂
     * @property create 创建经纪服务实例
     */
    public companion object Factory {
        /**
         * 指定加载的服务
         */
        internal const val NAME_KEY: String = "xyz.cssxsh.mirai.economy.service"

        /**
         * [IEconomyService] 加载器
         */
        internal val loaders: MutableList<ServiceLoader<IEconomyService>> = ArrayList()

        init {
            loaders.add(ServiceLoader.load(IEconomyService::class.java, IEconomyService::class.java.classLoader))
        }

        /**
         * 创建经纪服务实例
         * @param name 指定的服务名称
         * @throws UnsupportedOperationException 无法实例化插件服务
         * @see NAME_KEY System Property Key，可以用于指定服务
         * @see loaders 可用的服务加载器示例
         */
        @Throws(ServiceConfigurationError::class)
        public fun create(name: String?): IEconomyService {
            for (loader in loaders) {
                for (provider in loader.stream()) {
                    val clazz = provider.type()
                    val annotation = clazz.getAnnotation(EconomyServiceInfo::class.java)

                    if (name != null && name != annotation?.name) continue

                    val service = try {
                        provider.get() as AbstractEconomyService
                    } catch (error: ServiceConfigurationError) {
                        val cause = error.cause ?: error
                        if (annotation?.ignore.orEmpty().any { it.isInstance(cause) }) {
                            continue
                        } else {
                            throw error
                        }
                    } catch (cause: ClassCastException) {
                        if (annotation?.ignore.orEmpty().any { it.isInstance(cause) }) {
                            continue
                        } else {
                            throw ServiceConfigurationError("未继承 AbstractEconomyService", cause)
                        }
                    }

                    service.launch {
                        try {
                            EconomyServiceInitEvent(service = service).broadcast()
                        } catch (_: CancellationException) {
                            //
                        }
                    }

                    return service
                }
            }

            throw ServiceConfigurationError("无法创建 EconomyService 服务")
        }
    }
}

/**
 * EconomyService 抽象实现
 * 实现 [IEconomyService] 时应继承此类
 * @see IEconomyService
 */
public abstract class AbstractEconomyService : IEconomyService, CoroutineScope {
    protected open val logger: MiraiLogger = MiraiLogger.Factory.create(this::class.java)
    protected open var folder: Path = Path.of("economy")

    override fun reload(folder: Path) {
        this.folder = folder
        // ...
    }

    // region Currency

    protected open val currencies: MutableMap<String, EconomyCurrency> = ConcurrentHashMap()

    override val basket: Map<String, EconomyCurrency> get() = currencies.asImmutable()

    @Synchronized
    public override fun register(currency: EconomyCurrency, override: Boolean) {
        if (currency.id !in basket || override) {
            val event = EconomyCurrencyRegisteredEvent(
                currency = currency,
                service = this
            )
            broadcast(event) {
                currencies[currency.id] = currency
            }
        } else {
            throw UnsupportedOperationException("货币已存在")
        }
    }

    protected abstract val hard: HardCurrencyDelegate

    // endregion
}


/**
 * 经济服务名注解，用于标记一个服务的名称, 用于在服务初始化时匹配服务
 * @param name 服务的名称
 * @param ignore 初始化失败时, 忽略的错误类型
 * @see IEconomyService.create
 */
@Target(AnnotationTarget.CLASS)
public annotation class EconomyServiceInfo(
    val name: String,
    val ignore: Array<KClass<out Throwable>> = []
)