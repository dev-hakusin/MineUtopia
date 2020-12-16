package net.mineutopia.rpg.item

import net.mineutopia.rpg.skill.AttackSkill

interface Weapon: CustomItem {
    val strength: Double

    fun getSkillDamage(skill: AttackSkill): Double {
        return skill.damage + this.strength
    }
}