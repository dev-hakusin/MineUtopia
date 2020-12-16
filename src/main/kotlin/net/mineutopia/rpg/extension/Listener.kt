package net.mineutopia.rpg.extension

import net.mineutopia.rpg.MineUtopia
import org.bukkit.event.Listener

fun Listener.register() = MineUtopia.PLUGIN.let {
    it.server.pluginManager.registerEvents(this, it)
}