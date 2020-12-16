package net.mineutopia.rpg.item

import org.bukkit.ChatColor

enum class Rarity(val color: ChatColor) {
    LEGENDARY(ChatColor.GOLD),
    EPIC(ChatColor.DARK_PURPLE),
    RARE(ChatColor.BLUE),
    COMMON(ChatColor.WHITE),
    SYSTEM(ChatColor.RED)
}