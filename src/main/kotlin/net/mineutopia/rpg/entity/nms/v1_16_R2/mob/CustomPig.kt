package net.mineutopia.rpg.entity.nms.v1_16_R2.mob

import net.minecraft.server.v1_16_R2.*
import net.mineutopia.rpg.extension.customId
import net.mineutopia.rpg.entity.CustomMob
import net.mineutopia.rpg.entity.MobStats
import net.mineutopia.rpg.util.MobNameUtil
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld
import org.bukkit.event.entity.CreatureSpawnEvent
import net.minecraft.server.v1_16_R2.EntityHuman

import net.minecraft.server.v1_16_R2.PathfinderGoalNearestAttackableTarget
import net.minecraft.server.v1_16_R2.PathfinderGoalLookAtPlayer

import net.minecraft.server.v1_16_R2.PathfinderGoalFloat

import net.minecraft.server.v1_16_R2.PathfinderGoalMeleeAttack

import net.minecraft.server.v1_16_R2.GenericAttributes

import net.minecraft.server.v1_16_R2.AttributeModifiable
import org.bukkit.attribute.Attribute
import org.bukkit.craftbukkit.v1_16_R2.attribute.CraftAttributeMap
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftLivingEntity
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import java.lang.Exception
import java.lang.reflect.Field

class CustomPig(loc: Location) : EntityPig(EntityTypes.PIG, (loc.world as CraftWorld).handle) {
    companion object :CustomMob {
        override val id: String = "custompig"
        override val basename: String = "ぶたくん"
        override val stats: MobStats = MobStats(1, 20.0, 0.0)
    }

    init {
        this.setPositionRotation(loc.x, loc.y, loc.z, loc.pitch, loc.yaw)
        this.isBaby = false

        (loc.world as CraftWorld).handle.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM)
        bukkitEntity.apply {
            this.customId = Companion.id
            this.customName = MobNameUtil.getFormatDisplayName(basename, stats)

            (this as LivingEntity).getAttribute(Attribute.GENERIC_MAX_HEALTH)?.baseValue = stats.health
        }
    }

    override fun initPathfinder() {
        attributeMap.b().add(AttributeModifiable(
            GenericAttributes.ATTACK_DAMAGE
        ) { a: AttributeModifiable -> a.value = 1.0 })
        attributeMap.b().add(AttributeModifiable(
            GenericAttributes.FOLLOW_RANGE
        ) { a: AttributeModifiable -> a.value = 1.0 })
        try {
            registerGenericAttribute(bukkitEntity, Attribute.GENERIC_ATTACK_DAMAGE)
            registerGenericAttribute(bukkitEntity, Attribute.GENERIC_FOLLOW_RANGE)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

        goalSelector.a(0, PathfinderGoalMeleeAttack(this, 1.0, false))

        targetSelector.a(0, PathfinderGoalNearestAttackableTarget(this, EntityHuman::class.java, true))
        goalSelector.a(0, PathfinderGoalFloat(this))
        goalSelector.a(2, PathfinderGoalLookAtPlayer(this, EntityHuman::class.java, 8.0f))
    }

    @Throws(IllegalAccessException::class)
    fun registerGenericAttribute(entity: Entity, attribute: Attribute?) {
        val attributeMapBase = (entity as CraftLivingEntity).handle.attributeMap
        val attributeField: Field
        try {
            attributeField = AttributeMapBase::class.java.getDeclaredField("b")
            attributeField.isAccessible = true
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
            return
        }
        val map = attributeField[attributeMapBase] as MutableMap<*, *>
        try {
            @Suppress("UNCHECKED_CAST")
            map as MutableMap<AttributeBase, AttributeModifiable>
        } catch (e: Exception) {
            return
        }
        val attributeBase = CraftAttributeMap.toMinecraft(attribute)
        val attributeModifiable = AttributeModifiable(
            attributeBase
        ) { obj: AttributeModifiable -> obj.attribute }
        map[attributeBase] = attributeModifiable
    }
}
