package xyz.cssxsh.mirai.economy

import xyz.cssxsh.mirai.economy.service.EconomyCurrency

object EconomyServiceLaoLittleCoin : EconomyCurrency {
    override val id: String = "lao-little"
    override val name: String = "LaoLittle"
    override val description: String = "...."

    override fun format(amount: Double): String = "$amount 枚 LaoLittle 币"
}