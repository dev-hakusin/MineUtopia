package net.mineutopia.rpg.config

import net.mineutopia.rpg.MineUtopia
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

abstract class SimpleConfiguration(private val fileName: String) : YamlConfiguration() {

    private val plugin = MineUtopia.PLUGIN

    fun init(plugin: JavaPlugin) {
        val file = File(plugin.dataFolder, "$fileName.yml")
        if (!file.exists()) {
            plugin.logger.info("Config:${fileName}が存在しませんでした。新規作成します。")
            this.makeFile(file, plugin, fileName)
        }
        this.load(file)
    }

    protected open fun makeFile(file: File, plugin: JavaPlugin, fileName: String) {
        plugin.saveResource(file.name, false)
    }
}