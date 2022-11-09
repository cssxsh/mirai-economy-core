package xyz.cssxsh.mirai.economy

import org.junit.jupiter.api.BeforeAll
import java.nio.file.Path
import kotlin.io.path.writeText

internal class EconomyServiceH2Test : EconomyServiceTest() {
    @BeforeAll
    fun reload() {
        config.resolve( "hibernate.properties").writeText(
            """
                hibernate.connection.url=jdbc:h2:file:debug-sandbox/data/xyz.cssxsh.mirai.plugin.mirai-economy-core/record.h2
                hibernate.dialect=org.hibernate.dialect.H2Dialect
                hibernate.connection.provider_class=org.hibernate.hikaricp.internal.HikariCPConnectionProvider
                hibernate.connection.isolation=1
                hibernate.hbm2ddl.auto=update
                hibernate-connection-autocommit=true
            """.trimIndent()
        )
        EconomyService.reload(folder = data)
    }
}