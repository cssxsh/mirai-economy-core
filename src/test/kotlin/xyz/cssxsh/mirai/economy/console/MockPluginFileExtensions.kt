package xyz.cssxsh.mirai.economy.console

import net.mamoe.mirai.console.plugin.*
import java.io.File
import java.nio.file.Path

internal class MockPluginFileExtensions : PluginFileExtensions {
    override val configFolderPath: Path = Path.of("debug-sandbox", "config", "xyz.cssxsh.mirai.plugin.mirai-economy-core")
    override val configFolder: File = configFolderPath.toFile()
    override val dataFolderPath: Path = Path.of("debug-sandbox", "data", "xyz.cssxsh.mirai.plugin.mirai-economy-core")
    override val dataFolder: File = dataFolderPath.toFile()
}