package net.mineutopia.rpg.item

import org.bukkit.event.player.PlayerInteractEvent

interface Clickable {
    fun onClick(event: PlayerInteractEvent)
}