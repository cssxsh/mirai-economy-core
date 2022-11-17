package xyz.cssxsh.mirai.economy.service


/**
 * 经济事务类
 * @since 1.0.3
 */
public class EconomyTransaction internal constructor(
    private val context: EconomyAction,
    public val currency: EconomyCurrency,
    private val balance: MutableMap<EconomyAccount, Double>
) : MutableMap<EconomyAccount, Double> by balance {
    @Synchronized
    override fun get(key: EconomyAccount): Double {
        return balance.getOrPut(key) {
            with(context) {
                key[currency]
            }
        }
    }
}