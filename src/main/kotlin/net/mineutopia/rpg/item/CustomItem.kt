package net.mineutopia.rpg.item

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

interface CustomItem {
    val id: String

    val material: Material
    val displayName: String
    val lore: MutableList<String>

    val price: Int
    val rarity: Rarity

    fun toItemStack() : ItemStack

    fun register() {
        ItemData.register(this)
    }
}