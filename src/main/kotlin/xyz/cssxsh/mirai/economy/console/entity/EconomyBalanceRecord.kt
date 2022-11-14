package xyz.cssxsh.mirai.economy.console.entity

import jakarta.persistence.*

@PublishedApi
@Entity
@Table(name = "economy_balance_record")
@kotlinx.serialization.Serializable
internal data class EconomyBalanceRecord(
    @EmbeddedId
    val index: EconomyAccountIndex,
    @Column(name = "balance", nullable = false, updatable = true)
    val balance: Double
) : java.io.Serializable {
    @Column(name = "latest", nullable = false, updatable = true)
    val latest: Long = System.currentTimeMillis()
}