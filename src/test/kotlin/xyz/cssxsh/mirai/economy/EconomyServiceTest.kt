package xyz.cssxsh.mirai.economy

import org.junit.jupiter.api.*
import xyz.cssxsh.mirai.economy.service.EconomyCurrency

internal class EconomyServiceTest {
    object EconomyServiceTestCoin : EconomyCurrency {
        override val id: String = "test"
        override val name: String = "TEST 币"
        override val description: String = "...."

        override fun format(amount: Double): String = "$amount 枚 TEST 币"
    }

    @Test
    fun `register currency`() {
        EconomyService.register(EconomyServiceTestCoin)
    }

    @Test
    fun `global economy`() {
        val test1 = EconomyService.account(uuid = "test1", description = "test")
        globalEconomy {
            val test2 = service.account(uuid = "test2", description = "test")

            val v1 = test1[EconomyServiceTestCoin]
            val v2 = test2[EconomyServiceTestCoin]
            test1[EconomyServiceTestCoin] = 1000.0

            test1 += (EconomyServiceTestCoin to 100.0)

            test1 -= (EconomyServiceTestCoin to 10.0)

            test1 *= (EconomyServiceTestCoin to 10.0)

            test1 /= (EconomyServiceTestCoin to 5.0)
        }
    }
}