package xyz.cssxsh.mirai.economy.console

import xyz.cssxsh.mirai.economy.service.*

public object MiraiCoin : EconomyCurrency {
    override val id: String = "mirai-coin"
    override val name: String = "Mirai 币"
    override val description: String = "默认主货币"

    override fun format(amount: Double): String = "$amount 枚 Mirai 币"
}