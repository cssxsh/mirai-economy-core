package xyz.cssxsh.mirai.economy.console.entity

import jakarta.persistence.*

@PublishedApi
@Entity
@Table(name = "economy_hard_record")
@kotlinx.serialization.Serializable
internal class EconomyHardRecord(
    @Id
    @Column(name = "context", nullable = false, updatable = false)
    val context: String,
    @Column(name = "currency", nullable = false)
    val currency: String,
) : java.io.Serializable