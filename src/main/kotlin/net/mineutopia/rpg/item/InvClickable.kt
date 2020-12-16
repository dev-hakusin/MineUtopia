package net.mineutopia.rpg.item

import org.bukkit.event.inventory.InventoryClickEvent

interface InvClickable {
    fun onInvClick(event: InventoryClickEvent)
}