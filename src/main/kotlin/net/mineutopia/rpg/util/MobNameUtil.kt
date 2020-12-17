package net.mineutopia.rpg.util

import net.mineutopia.rpg.entity.MobStats
import org.bukkit.ChatColor

object MobNameUtil {
    fun getFormatDisplayName(level: Int, name: String, health: Double, maxHealth: Double): String {
        return "${ChatColor.GOLD}[Lv${level}]${ChatColor.RESET} " +
                "$name " +
                "${ChatColor.RED}${health.toInt()}" +
                "${ChatColor.GREEN}/" +
                "${ChatColor.RED}${maxHealth.toInt()}"
    }

    fun getFormatDisplayName(name: String, mobStats: MobStats): String {
        return getFormatDisplayName(mobStats.level, name, mobStats.health, mobStats.health)
    }
}