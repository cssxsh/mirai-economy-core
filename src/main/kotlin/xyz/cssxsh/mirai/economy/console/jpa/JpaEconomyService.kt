package xyz.cssxsh.mirai.economy.console.jpa

import kotlinx.coroutines.*
import net.mamoe.mirai.*
import net.mamoe.mirai.console.plugin.*
import net.mamoe.mirai.contact.*
import net.mamoe.mirai.utils.*
import org.hibernate.*
import xyz.cssxsh.mirai.economy.console.*
import xyz.cssxsh.mirai.economy.console.entity.*
import xyz.cssxsh.mirai.economy.service.*
import xyz.cssxsh.mirai.hibernate.*
import java.nio.file.*
import java.util.*
import kotlin.coroutines.*
import kotlin.reflect.*

@PublishedApi
@EconomyServiceInfo(name = "jpa", ignore = [NoClassDefFoundError::class])
internal class JpaEconomyService : IEconomyService, AbstractEconomyService() {
    override val id: String = "economy.service.jpa"

    private lateinit var factory: SessionFactory

    override val coroutineContext: CoroutineContext = kotlin.run {
        try {
            MiraiEconomyCorePlugin.coroutineContext
        } catch (_: UninitializedPropertyAccessException) {
            CoroutineExceptionHandler { _, throwable ->
                if (throwable.unwrapCancellationException() !is CancellationException) {
                    logger.error("Exception in coroutine JpaEconomyService", throwable)
                }
            }
        } + CoroutineName(id)
    }

    @Synchronized
    override fun reload(folder: Path) {
        this.folder = folder
        val files: PluginFileExtensions = try {
            MiraiEconomyCorePlugin.dataFolder
            MiraiEconomyCorePlugin
        } catch (_: UninitializedPropertyAccessException) {
            ServiceLoader.load(PluginFileExtensions::class.java, this::class.java.classLoader)
                .first()
        }
        this.factory = MiraiHibernateConfiguration(loader = MiraiHibernateLoader.Impl(files))
            .buildSessionFactory()
    }

    @Synchronized
    override fun flush() {
        this.factory.cache.evictAll()
    }

    @Synchronized
    override fun close() {
        this.factory.close()
    }

    // region Currency

    public override val hard: HardCurrencyDelegate = object : HardCurrencyDelegate {
        override fun getValue(thisRef: EconomyContext, property: KProperty<*>): EconomyCurrency {
            return factory.fromSession { session ->
                TODO("get hard")
            }
        }

        override fun setValue(thisRef: EconomyContext, property: KProperty<*>, value: EconomyCurrency) {
            factory.fromTransaction { session ->
                TODO("set hard")
            }
        }
    }

    // endregion

    // region Context

    override fun global(): GlobalEconomyContext {
        return JpaGlobalEconomyContext(session = factory.openSession(), service = this)
    }

    override fun context(target: Bot): BotEconomyContext {
        return JpaBotEconomyContext(session = factory.openSession(), service = this, bot = target)
    }

    override fun context(target: Group): GroupEconomyContext {
        return JpaGroupEconomyContext(session = factory.openSession(), service = this, group = target)
    }

    // endregion

    // region Account

    override fun account(user: User): UserEconomyAccount {
        return JpaUserEconomyAccount(
            record = factory.fromTransaction { session ->
                val record = EconomyAccountRecord.fromUser(user = user)
                session.merge(user)
                session.flush()
                record
            },
            user = user
        )
    }

    override fun account(group: Group): GroupEconomyAccount {
        return JpaGroupEconomyAccount(
            record = factory.fromTransaction { session ->
                val record = EconomyAccountRecord.fromGroup(group = group)
                session.merge(record)
                session.flush()
                record
            },
            group = group
        )
    }

    override fun account(uuid: String, description: String?): EconomyAccount {
        return JpaCustomEconomyAccount(
            record = factory.fromTransaction { session ->
                val record = EconomyAccountRecord.fromInfo(uuid = uuid, description = description)
                session.merge(record)
                session.flush()
                record
            }
        )
    }

    // endregion
}