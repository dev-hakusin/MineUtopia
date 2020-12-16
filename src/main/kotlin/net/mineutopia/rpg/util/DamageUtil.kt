package net.mineutopia.rpg.util

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.mineutopia.rpg.extension.damageAttribution
import net.mineutopia.rpg.skill.DamageAttribution
import org.bukkit.Location
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player

object DamageUtil {
    fun setDamage(entity: LivingEntity, damage: Double, damageAttribution: DamageAttribution, damager: Player) {
        val targetAttribution = entity.damageAttribution
        entity.damage(getDamageFromAttributionCorrelation(damage, damageAttribution, targetAttribution), damager)
        spawnDamageView(entity.eyeLocation, damage, damageAttribution)
    }

    private fun getDamageFromAttributionCorrelation(
            defaultDamage: Double,
            damageAttribution: DamageAttribution,
            targetAttribution: DamageAttribution): Double {

        when (damageAttribution) {
            //全補正無し
            DamageAttribution.PHYSICAL -> {
                return defaultDamage
            }
            //水に弱, 風に強
            DamageAttribution.FIRE -> {
                return when (targetAttribution) {
                    DamageAttribution.AQUA -> {
                        defaultDamage * 0.8
                    }
                    DamageAttribution.WIND -> {
                        defaultDamage * 1.2
                    }
                    else -> {
                        defaultDamage
                    }
                }
            }
            //土に弱, 火に強
            DamageAttribution.AQUA -> {
                return when (targetAttribution) {
                    DamageAttribution.MINE -> {
                        defaultDamage * 0.8
                    }
                    DamageAttribution.FIRE -> {
                        defaultDamage * 1.2
                    }
                    else -> {
                        defaultDamage
                    }
                }
            }
            //風に弱, 水に強
            DamageAttribution.MINE -> {
                return when (targetAttribution) {
                    DamageAttribution.WIND -> {
                        defaultDamage * 0.8
                    }
                    DamageAttribution.AQUA -> {
                        defaultDamage * 1.2
                    }
                    else -> {
                        defaultDamage
                    }
                }
            }
            //火に弱, 土に強
            DamageAttribution.WIND -> {
                return when (targetAttribution) {
                    DamageAttribution.FIRE -> {
                        defaultDamage * 0.8
                    }
                    DamageAttribution.MINE -> {
                        defaultDamage * 1.2
                    }
                    else -> {
                        defaultDamage
                    }
                }
            }
            DamageAttribution.DARK -> {
                return when (targetAttribution) {
                    DamageAttribution.HOLLY -> {
                        defaultDamage * 1.5
                    }
                    else -> {
                        defaultDamage
                    }
                }
            }
            DamageAttribution.HOLLY -> {
                return when (targetAttribution) {
                    DamageAttribution.DARK -> {
                        defaultDamage * 1.5
                    }
                    else -> {
                        defaultDamage
                    }
                }
            }
            else -> return defaultDamage
        }
    }

    private fun spawnDamageView(loc: Location, damage: Double, damageAttribution: DamageAttribution) {
        loc.add(0.0, 0.2, 0.0)
        //TODO Locationにランダム
        val entity = loc.world?.spawnEntity(loc, EntityType.ARMOR_STAND).apply {
            (this as ArmorStand).isSmall = true
            this.isMarker = true
            this.isInvisible = true
            this.customName = "${damageAttribution.color}${String.format("%.1f", damage)}"
            this.isCustomNameVisible = true
        }
        GlobalScope.launch {
            delay(500)
            entity?.remove()
        }
    }
}