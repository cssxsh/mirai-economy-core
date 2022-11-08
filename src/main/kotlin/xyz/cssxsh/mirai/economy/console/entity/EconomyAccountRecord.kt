package xyz.cssxsh.mirai.economy.console.entity

import jakarta.persistence.*
import net.mamoe.mirai.console.permission.PermitteeId.Companion.permitteeId
import net.mamoe.mirai.console.util.ContactUtils.render
import net.mamoe.mirai.contact.*

@PublishedApi
@Entity
@Table(name = "economy_account_record")
@kotlinx.serialization.Serializable
internal class EconomyAccountRecord(
    @Id
    @Column(name = "uuid", nullable = false, updatable = false)
    val uuid: String,
    @Column(name = "description", nullable = false)
    val description: String,
    @Column(name = "created", nullable = false, updatable = false)
    val created: Long = System.currentTimeMillis()
) : java.io.Serializable {
    companion object {
        fun fromUser(user: User): EconomyAccountRecord = EconomyAccountRecord(
            uuid = user.permitteeId.asString(),
            description = user.render()
        )

        fun fromGroup(group: Group): EconomyAccountRecord = EconomyAccountRecord(
            uuid = group.permitteeId.asString(),
            description = group.render()
        )

        fun fromInfo(uuid: String, description: String?): EconomyAccountRecord = EconomyAccountRecord(
            uuid = uuid,
            // XXX: ...
            description = description ?: ""
        )
    }
}