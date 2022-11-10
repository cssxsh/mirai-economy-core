package xyz.cssxsh.mirai.economy

import kotlinx.coroutines.*
import net.mamoe.mirai.event.*
import net.mamoe.mirai.event.events.*
import org.junit.jupiter.api.*
import xyz.cssxsh.mirai.economy.event.*
import xyz.cssxsh.mirai.economy.service.*
import java.nio.file.Path

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal abstract class EconomyServiceTest {
    protected val config: Path = Path.of("debug-sandbox", "config", "xyz.cssxsh.mirai.plugin.mirai-economy-core")
    protected val data: Path = Path.of("debug-sandbox", "data", "xyz.cssxsh.mirai.plugin.mirai-economy-core")

    init {
        config.toFile().mkdirs()
        data.toFile().mkdirs()
        EconomyServiceTestListenerHost.registerTo(GlobalEventChannel)
    }

    @AfterAll
    fun close() {
        EconomyService.close()
        EconomyServiceTestListenerHost.cancel()
    }

    @Test
    fun `register currency`() {
        EconomyService.register(EconomyServiceTestCoin)
        Assertions.assertThrows(EconomyEventCancelledException::class.java) {
            EconomyService.register(EconomyServiceLaoLittleCoin)
        }
        Assertions.assertFalse(EconomyServiceLaoLittleCoin.id in EconomyService.basket)
    }

    @Test
    fun `global economy`() {
        val test1 = EconomyService.account(uuid = "test1", description = "test")
        globalEconomy {
            val test2 = service.account(uuid = "test2", description = "test")

            Assertions.assertTrue(test1[EconomyServiceTestCoin] >= 0.0)
            Assertions.assertEquals(0.0, test2[EconomyServiceTestCoin])

            test1[EconomyServiceTestCoin] = 1000.0
            Assertions.assertEquals(1000.0, test1[EconomyServiceTestCoin])
            test1 += (EconomyServiceTestCoin to 100.0)
            Assertions.assertEquals(1100.0, test1[EconomyServiceTestCoin])
            test1 -= (EconomyServiceTestCoin to 10.0)
            Assertions.assertEquals(1090.0, test1[EconomyServiceTestCoin])
            test1 *= (EconomyServiceTestCoin to 10.0)
            Assertions.assertEquals(10900.0, test1[EconomyServiceTestCoin])
            test1 /= (EconomyServiceTestCoin to 5.0)
            Assertions.assertEquals(2180.0, test1[EconomyServiceTestCoin])
        }
    }

    @Test
    fun `hard currency`() {
        globalEconomy {
            try {
                hard
            } catch (_: UnsupportedOperationException) {
                //
            }
            hard = EconomyServiceTestCoin
            Assertions.assertEquals(EconomyServiceTestCoin.id, hard.id)
        }
    }
}