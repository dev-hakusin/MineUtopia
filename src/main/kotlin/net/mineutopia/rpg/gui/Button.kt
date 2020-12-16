package net.mineutopia.rpg.gui

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

interface Button {
    val material: Material
    val buttonName: String
    val buttonLore: MutableList<String>

    fun runAction(event: InventoryClickEvent)

    fun toItemStack(player: Player): ItemStack
}