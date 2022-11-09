package xyz.cssxsh.mirai.economy

import org.junit.jupiter.api.BeforeAll
import kotlin.io.path.writeText

internal class EconomyServiceMysqlTest : EconomyServiceTest() {
    @BeforeAll
    fun reload() {
        config.resolve( "hibernate.properties").writeText(
            """
                hibernate.connection.url=jdbc:mysql://localhost:3306/mirai?autoReconnect=true
                hibernate.connection.CharSet=utf8mb4
                hibernate.connection.useUnicode=true
                hibernate.connection.username=root
                hibernate.connection.password=root
                hibernate.dialect=org.hibernate.dialect.MariaDBDialect
                hibernate.connection.provider_class=org.hibernate.hikaricp.internal.HikariCPConnectionProvider
                hibernate.connection.isolation=1
                hibernate.hbm2ddl.auto=update
                hibernate.autoReconnect=true
            """.trimIndent()
        )
        EconomyService.reload(folder = data)
    }
}