package net.mineutopia.rpg.skill

import net.mineutopia.rpg.item.Weapon
import org.bukkit.entity.Player

interface Skill {
    val id: String
    val ct: Long

    //TODO 型定義変更するかも
    val cost: Int

    fun run(player: Player, item: Weapon)

    fun register() {
        SkillData.register(this)
    }
}