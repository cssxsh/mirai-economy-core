package xyz.cssxsh.mirai.economy

import org.junit.jupiter.api.BeforeAll
import java.nio.file.Path
import kotlin.io.path.writeText

internal class EconomyServicePostgreSqlTest : EconomyServiceTest() {
    @BeforeAll
    fun reload() {
        config.writeText(
            """
                hibernate.connection.url=jdbc:postgresql://localhost:5432/mirai?autoReconnect=true
                hibernate.connection.username=postgres
                hibernate.connection.password=root
                hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
                hibernate.connection.provider_class=org.hibernate.hikaricp.internal.HikariCPConnectionProvider
                hibernate.connection.isolation=1
                hibernate.hbm2ddl.auto=update
                hibernate.autoReconnect=true
            """.trimIndent()
        )
        EconomyService.reload(folder = Path.of("debug-sandbox", "data"))
    }
}