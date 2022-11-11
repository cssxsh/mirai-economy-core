package xyz.cssxsh.mirai.economy.console.jpa

import net.mamoe.mirai.contact.*
import xyz.cssxsh.mirai.economy.console.entity.*
import xyz.cssxsh.mirai.economy.service.*

@PublishedApi
internal class JpaGroupEconomyAccount(
    private val record: EconomyAccountRecord,
    override val group: Group
) : GroupEconomyAccount, AbstractEconomyAccount() {
    override val uuid: String get() = record.uuid
    override val description: String get() = record.description
}