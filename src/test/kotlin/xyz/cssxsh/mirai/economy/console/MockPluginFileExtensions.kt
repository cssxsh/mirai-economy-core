package xyz.cssxsh.mirai.economy.console

import net.mamoe.mirai.console.plugin.*
import java.io.File
import java.nio.file.Path

internal class MockPluginFileExtensions : PluginFileExtensions {
    override val configFolderPath: Path = Path.of("debug-sandbox", "config")
    override val configFolder: File = configFolderPath.toFile()
    override val dataFolderPath: Path = Path.of("debug-sandbox", "data")
    override val dataFolder: File = dataFolderPath.toFile()
}