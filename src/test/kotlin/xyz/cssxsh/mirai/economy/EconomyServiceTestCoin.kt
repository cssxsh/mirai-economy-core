package xyz.cssxsh.mirai.economy

import xyz.cssxsh.mirai.economy.service.EconomyCurrency

object EconomyServiceTestCoin : EconomyCurrency {
    override val id: String = "test"
    override val name: String = "TEST 币"
    override val description: String = "...."

    override fun format(amount: Double): String = "$amount 枚 TEST 币"
}