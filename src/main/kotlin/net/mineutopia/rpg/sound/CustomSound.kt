package net.mineutopia.rpg.sound

import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.entity.Player

class CustomSound(
        private val sound: Sound,
        private val category: SoundCategory,
        private val volume: Float = 1.0F,
        private val pitch: Float = 1.0F
) {
    fun play(player: Player) {
        player.playSound(player.location, sound, category, volume, pitch)
    }

    fun play(loc: Location) {
        loc.world?.playSound(loc, sound, category, volume, pitch)
    }
}
