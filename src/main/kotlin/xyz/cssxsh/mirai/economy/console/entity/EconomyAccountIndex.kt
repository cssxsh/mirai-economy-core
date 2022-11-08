package xyz.cssxsh.mirai.economy.console.entity

import jakarta.persistence.*

@PublishedApi
@Embeddable
@kotlinx.serialization.Serializable
internal data class EconomyAccountIndex(
    @Column(name = "uuid", nullable = false, updatable = false)
    val uuid: String,
    @Column(name = "currency", nullable = false, updatable = false)
    val currency: String,
    @Column(name = "context", nullable = false, updatable = false)
    val context: String,
) : java.io.Serializable