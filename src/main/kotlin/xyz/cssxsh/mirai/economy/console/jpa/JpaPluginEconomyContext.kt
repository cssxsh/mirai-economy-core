package xyz.cssxsh.mirai.economy.console.jpa

import net.mamoe.mirai.console.plugin.*
import net.mamoe.mirai.console.plugin.jvm.*
import net.mamoe.mirai.utils.*
import org.hibernate.*
import xyz.cssxsh.mirai.economy.service.*

@PublishedApi
internal class JpaPluginEconomyContext(
    override val session: Session,
    override val service: JpaEconomyService,
    val plugin: JvmPlugin
) : GlobalEconomyContext, JpaSessionAction() {
    override val id: String = plugin.id

    override val logger: MiraiLogger = MiraiLogger.Factory.create(this::class, id)

    override var hard: EconomyCurrency by service.hard

    override val context: String get() = id
}