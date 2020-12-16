package net.mineutopia.rpg

import net.mineutopia.rpg.command.CommandDebug
import net.mineutopia.rpg.command.CommandGet
import net.mineutopia.rpg.config.DatabaseConfig
import net.mineutopia.rpg.config.SimpleConfiguration
import net.mineutopia.rpg.extension.register
import net.mineutopia.rpg.item.CustomItem
import net.mineutopia.rpg.item.items.MenuItem
import net.mineutopia.rpg.item.items.backpack.BackPack
import net.mineutopia.rpg.item.items.skillbook.DebugSkillBook
import net.mineutopia.rpg.item.items.weapon.wand.DebugWand
import net.mineutopia.rpg.listener.EntityListener
import net.mineutopia.rpg.listener.InventoryListener
import net.mineutopia.rpg.listener.PlayerListener
import net.mineutopia.rpg.listener.SnowBallListener
import net.mineutopia.rpg.mob.CustomMob
import net.mineutopia.rpg.mob.nms.v1_16_R2.CustomPig
import net.mineutopia.rpg.skill.Skill
import net.mineutopia.rpg.skill.skills.DebugSkill
import net.mineutopia.rpg.util.DatabaseUtil
import org.bukkit.command.CommandExecutor
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class MineUtopia : JavaPlugin() {
    companion object {
        lateinit var PLUGIN: MineUtopia
    }

    override fun onEnable() {
        // Plugin startup logic
        PLUGIN = this

        loadConfiguration(
                DatabaseConfig
        )
        registerListeners(
                PlayerListener(),
                EntityListener(),
                InventoryListener(),
                SnowBallListener()
        )
        registerCustomItems(
                DebugWand,
                DebugSkillBook,
                BackPack,
                MenuItem
        )
        registerCustomMobs(
            CustomPig
        )
        registerSkills(
                DebugSkill
        )

        registerCommands(
                Commands("get", CommandGet),
                Commands("debug", CommandDebug)
        )

        DatabaseUtil.prepareDatabase()

        server.setWhitelist(false)
    }

    override fun onDisable() {
        // Plugin shutdown logic
        server.setWhitelist(true)
        server.onlinePlayers.forEach {
            it.kickPlayer("server closed")
        }
    }

    private fun loadConfiguration(vararg configurations: SimpleConfiguration) = configurations.forEach { it.init(this) }
    private fun registerListeners(vararg listeners: Listener) { listeners.forEach { it.register() } }

    private fun registerCustomItems(vararg items: CustomItem) {items.forEach { it.register() }}
    private fun registerCustomMobs(vararg mobs: CustomMob) {mobs.forEach { it.register() }}
    private fun registerSkills(vararg skills: Skill) {skills.forEach { it.register() }}

    private data class Commands(val name: String, val executor: CommandExecutor)
    private fun registerCommands(vararg commands: Commands) = commands.forEach { getCommand(it.name)?.setExecutor(it.executor) }
}