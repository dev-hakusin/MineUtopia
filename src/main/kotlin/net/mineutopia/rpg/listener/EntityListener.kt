package net.mineutopia.rpg.listener

import net.mineutopia.rpg.extension.customId
import net.mineutopia.rpg.mob.MobData
import net.mineutopia.rpg.util.MobNameUtil
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.*

class EntityListener: Listener {
    @EventHandler
    fun onTarget(event: EntityTargetLivingEntityEvent) {
        val entity = event.entity
        val target = event.target
        if (target !is Player) {
            event.isCancelled = true
        }
        if (target?.isInvulnerable == true) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onCombust(event: EntityCombustEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun onRegainHealth(event: EntityRegainHealthEvent) {
        val entity = event.entity
        if (entity !is Player) {
            if (entity is LivingEntity) {
                val customMob = MobData.get(entity.customId ?: return) ?: return
                entity.customName = MobNameUtil.getFormatDisplayName(
                    customMob.stats.level,
                    customMob.basename,
                    if (entity.health + event.amount >= customMob.stats.health) customMob.stats.health else entity.health + event.amount,
                    customMob.stats.health
                )
            }
        }
    }


    @EventHandler
    fun onDamage(event: EntityDamageEvent) {
        val entity = event.entity
        if (entity !is Player) {
            if (entity is LivingEntity) {
                val customMob = MobData.get(entity.customId ?: return) ?: return
                entity.customName = MobNameUtil.getFormatDisplayName(
                    customMob.stats.level,
                    customMob.basename,
                    entity.health - event.damage,
                    customMob.stats.health
                )
            }
        }
    }

    @EventHandler
    fun onDamageByEntity(event: EntityDamageByEntityEvent) {
        val entity = event.entity
        val damager = event.damager
        if (entity !is LivingEntity) {
            return
        }
        when (entity) {
            is Player -> {
                //TODO 実装
            }
            else -> {
                damager.sendMessage("test")
            }
        }
    }
}