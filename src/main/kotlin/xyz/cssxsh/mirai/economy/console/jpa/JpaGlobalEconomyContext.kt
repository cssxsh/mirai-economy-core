package xyz.cssxsh.mirai.economy.console.jpa

import net.mamoe.mirai.utils.*
import org.hibernate.*
import xyz.cssxsh.mirai.economy.service.*

@PublishedApi
internal class JpaGlobalEconomyContext(
    override val session: Session,
    override val service: JpaEconomyService
) : GlobalEconomyContext, JpaSessionAction() {
    override val id: String = "global-economy"

    override val logger: MiraiLogger = MiraiLogger.Factory.create(this::class, id)

    override var hard: EconomyCurrency by service.hard

    override val context: String get() = id
}