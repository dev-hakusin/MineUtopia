package net.mineutopia.rpg.skill

import org.bukkit.ChatColor

enum class DamageAttribution(val localizeName: String ,val color: ChatColor) {
    PHYSICAL("物理", ChatColor.GRAY),

    FIRE("炎", ChatColor.RED),
    AQUA("水", ChatColor.AQUA),
    MINE("土", ChatColor.YELLOW),
    WIND("風", ChatColor.GREEN),

    DARK("呪怨", ChatColor.BLACK),
    HOLLY("神聖", ChatColor.WHITE)
}