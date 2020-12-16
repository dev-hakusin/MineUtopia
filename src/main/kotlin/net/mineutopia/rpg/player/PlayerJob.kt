package net.mineutopia.rpg.player

import org.bukkit.ChatColor

enum class PlayerJob(val jobId: Int, val localizeName: String, val color: ChatColor) {
    TRAVELLER(0, "旅人", ChatColor.GREEN),
    SORCERER(1, "魔法使い", ChatColor.RED)

}