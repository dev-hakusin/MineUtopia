package net.mineutopia.rpg.listener

import net.mineutopia.rpg.extension.damage
import net.mineutopia.rpg.extension.damageAttribution
import net.mineutopia.rpg.util.DamageUtil
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.entity.Snowball
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileHitEvent

class SnowBallListener: Listener {
    @EventHandler
    fun onProjectileHit(event: ProjectileHitEvent) {
        val entity = event.entity
        if (entity is Snowball) {
            if (entity.customName.equals("MagicBullet")) {
                if (event.hitEntity != null) {
                    val target = event.hitEntity
                    val damage = entity.damage
                    val damageAttribution = entity.damageAttribution
                    if (target is LivingEntity && target !is Player) {
                        if (target.isInvulnerable) {
                            return
                        }
                        DamageUtil.setDamage(target, damage, damageAttribution, entity.shooter as Player)
                    }
                }
            }
        }
    }
}