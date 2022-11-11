package xyz.cssxsh.mirai.economy.console.jpa

import net.mamoe.mirai.contact.*
import net.mamoe.mirai.utils.*
import org.hibernate.*
import xyz.cssxsh.mirai.economy.service.*

@PublishedApi
internal class JpaGroupEconomyContext(
    override val session: Session,
    override val service: JpaEconomyService,
    override val group: Group
) : GroupEconomyContext, JpaSessionAction() {
    override val id: String = "group[${group.id}]-economy"

    override val logger: MiraiLogger = MiraiLogger.Factory.create(this::class, id)

    override var hard: EconomyCurrency by service.hard

    override val context: String get() = id
}