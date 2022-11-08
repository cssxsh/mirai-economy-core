package xyz.cssxsh.mirai.economy.console.jpa

import net.mamoe.mirai.*
import net.mamoe.mirai.utils.*
import org.hibernate.*
import xyz.cssxsh.mirai.economy.service.*

@PublishedApi
internal class JpaBotEconomyContext(
    override val session: Session,
    override val service: JpaEconomyService,
    override val bot: Bot
) : BotEconomyContext, JpaSessionAction() {
    override val id: String = "bot[${bot.id}]-economy"

    override val logger: MiraiLogger = MiraiLogger.Factory.create(this::class, id)

    override var hard: EconomyCurrency by service.hard
}