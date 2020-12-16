package net.mineutopia.rpg.item

import net.mineutopia.rpg.skill.Skill

interface SkillBook: CustomItem {
    val skill: Skill
}