package net.mineutopia.rpg.skill.skills

import net.mineutopia.rpg.item.Weapon
import net.mineutopia.rpg.skill.Skill
import org.bukkit.ChatColor
import org.bukkit.entity.Player

object ErrorSkill: Skill {
    //エラー時に使用され(たことにす)る
    override val id: String = "error"
    override val ct: Long = 0L
    override val cost: Int = 0

    override fun run(player: Player, item: Weapon) {
        player.sendMessage("${ChatColor.RED}設定されていません")
    }
}