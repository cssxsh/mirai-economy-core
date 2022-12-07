package xyz.cssxsh.mirai.economy.console.jpa

import kotlinx.coroutines.*
import net.mamoe.mirai.*
import net.mamoe.mirai.console.permission.*
import net.mamoe.mirai.console.plugin.*
import net.mamoe.mirai.contact.*
import net.mamoe.mirai.utils.*
import org.hibernate.*
import xyz.cssxsh.mirai.economy.console.*
import xyz.cssxsh.mirai.economy.console.entity.*
import xyz.cssxsh.mirai.economy.script.*
import xyz.cssxsh.mirai.economy.service.*
import xyz.cssxsh.mirai.hibernate.*
import java.nio.file.*
import java.util.*
import kotlin.coroutines.*
import kotlin.io.path.*
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
                if (throwable !is CancellationException) {
                    logger.error({ "Exception in coroutine JpaEconomyService" }, throwable)
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

        val currencies = folder.resolve("currencies")
        Files.createDirectories(currencies)
        for (entry in currencies.listDirectoryEntries()) {
            val currency = try {
                when {
                    entry.isDirectory() -> EconomyScriptCurrency.fromFolder(folder = entry)
                    entry.isReadable() -> EconomyScriptCurrency.fromZip(pack = entry)
                    else -> continue
                }
            } catch (_: NoSuchElementException) {
                continue
            }
            register(currency = currency, override = true)
        }
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
                val record = session[EconomyHardRecord::class.java, thisRef.id]
                    ?: return@fromSession MiraiCoin
                basket[record.currency]
                    ?: throw UnsupportedOperationException("找不到货币 ${record.currency}")
            }
        }

        override fun setValue(thisRef: EconomyContext, property: KProperty<*>, value: EconomyCurrency) {
            factory.fromTransaction { session ->
                val record = EconomyHardRecord(
                    context = thisRef.id,
                    currency = value.id
                )
                session.merge(record)
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
                session.merge(record)
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
        val record = factory.fromTransaction { session ->
            val record = EconomyAccountRecord.fromInfo(uuid = uuid, description = description)
            session.merge(record)
            session.flush()
            record
        }
        val permitteeId = try {
            AbstractPermitteeId.parseFromString(record.uuid)
        } catch (_: IllegalStateException) {
            null
        }
        when (permitteeId) {
            is AbstractPermitteeId.ExactUser -> {
                for (bot in Bot.instances) {
                    val friend = bot.friends[permitteeId.id]
                    if (friend != null) return JpaUserEconomyAccount(record = record, user = friend)
                    for (group in bot.groups) {
                        val member = group.members[permitteeId.id]
                        if (member != null) return JpaUserEconomyAccount(record = record, user = member)
                    }
                }
            }
            is AbstractPermitteeId.ExactGroup -> {
                for (bot in Bot.instances) {
                    val group = bot.groups[permitteeId.groupId] ?: continue
                    return JpaGroupEconomyAccount(record = record, group = group)
                }
            }
            else -> Unit
        }

        return JpaCustomEconomyAccount(record = record)
    }

    // endregion
}