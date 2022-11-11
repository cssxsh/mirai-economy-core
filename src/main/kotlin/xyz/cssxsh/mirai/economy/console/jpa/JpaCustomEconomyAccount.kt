package xyz.cssxsh.mirai.economy.console.jpa

import xyz.cssxsh.mirai.economy.console.entity.*
import xyz.cssxsh.mirai.economy.service.*

internal class JpaCustomEconomyAccount(
    private val record: EconomyAccountRecord
) : CustomEconomyAccount, AbstractEconomyAccount() {
    override val uuid: String get() = record.uuid
    override val description: String get() = record.description
    override val created: Long get() = record.created
}