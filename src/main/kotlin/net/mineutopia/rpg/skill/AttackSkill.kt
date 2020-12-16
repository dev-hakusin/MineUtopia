package net.mineutopia.rpg.skill

interface AttackSkill: Skill {
    val damageAttribution: DamageAttribution
    val damage: Double
}