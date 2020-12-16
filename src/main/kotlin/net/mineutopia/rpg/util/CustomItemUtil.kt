package net.mineutopia.rpg.util

import net.mineutopia.rpg.extension.customId
import net.mineutopia.rpg.extension.displayName
import net.mineutopia.rpg.extension.lore
import net.mineutopia.rpg.item.CustomItem
import net.mineutopia.rpg.item.Weapon
import org.bukkit.ChatColor
import org.bukkit.inventory.ItemStack

object CustomItemUtil {
    fun customItemToItemStack(customItem: CustomItem): ItemStack {
        return ItemStack(customItem.material).apply {
            this.customId = customItem.id
            this.displayName = "${customItem.rarity.color}${customItem.displayName}"
            val list = customItem.lore.toMutableList()
            if (customItem is Weapon) {
                list.add("")
                list.add("${ChatColor.RED}攻撃力: ${customItem.strength}")
            }
            list.add("")
            list.add("${ChatColor.RESET}${customItem.rarity.color}${ChatColor.BOLD}${customItem.rarity.name} ITEM")
            this.lore = list
        }
    }
}