package xyz.cssxsh.mirai.economy.console.jpa

import net.mamoe.mirai.contact.*
import xyz.cssxsh.mirai.economy.console.entity.*
import xyz.cssxsh.mirai.economy.service.*

@PublishedApi
internal class JpaUserEconomyAccount(
    private val record: EconomyAccountRecord,
    override val user: User
) : UserEconomyAccount {
    override val uuid: String get() = record.uuid
    override val description: String get() = record.description
}