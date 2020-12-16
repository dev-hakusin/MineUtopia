package net.mineutopia.rpg.skill.skills

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.mineutopia.rpg.MineUtopia
import net.mineutopia.rpg.extension.damage
import net.mineutopia.rpg.extension.damageAttribution
import net.mineutopia.rpg.item.Weapon
import net.mineutopia.rpg.skill.AttackSkill
import net.mineutopia.rpg.skill.DamageAttribution
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.entity.Snowball
import org.bukkit.scheduler.BukkitRunnable

object DebugSkill: AttackSkill {
    override val id: String = "debug"
    override val ct: Long = 1200
    override val cost: Int = 3
    override val damageAttribution: DamageAttribution = DamageAttribution.FIRE

    override val damage: Double = 10.0

    override fun run(player: Player, item: Weapon) {
        val loc = player.eyeLocation.apply {
            //スキルを使った瞬間に前が見えなくなるのを防ぐ
            y -= 0.2
        }

        val entity = (loc.world?.spawnEntity(loc, EntityType.SNOWBALL) ?: return) as Snowball
        entity.apply {
            this.setGravity(false)
            this.shooter = player
            this.customName = "MagicBullet"
            this.isCustomNameVisible = false

            this.damageAttribution = this@DebugSkill.damageAttribution
            //TODO 実装
            this.damage = 20.0
        }

        entity.velocity = loc.direction.apply {
            x *= 1.5
            y *= 1.5
            z *= 1.5
        }
        player.playSound(player.location, Sound.ENTITY_GHAST_SHOOT, 0.5f, 1.4f)

        GlobalScope.launch {
            val firstLocation = player.location
            while (true) {
                val eLoc = entity.location
                if (entity.isDead) {
                    break
                }
                if (eLoc.block.type == Material.WATER) {
                    object : BukkitRunnable() {
                        override fun run() {
                            eLoc.world?.spawnParticle(Particle.SMOKE_NORMAL, eLoc, 10, 0.0, 0.0, 0.0, 0.01)
                            eLoc.world?.playSound(eLoc, Sound.BLOCK_FIRE_EXTINGUISH, 1f, 1f)
                        }
                    }.runTask(MineUtopia.PLUGIN)
                    break
                }
                if (firstLocation.distance(eLoc) >= 25) {
                    break
                }
                delay(50)
                object : BukkitRunnable() {
                    override fun run() {
                        eLoc.world?.spawnParticle(Particle.FLAME, eLoc, 5, 0.0, 0.0, 0.0, 0.008)
                    }
                }.runTask(MineUtopia.PLUGIN)
            }
            entity.remove()
        }
    }
}