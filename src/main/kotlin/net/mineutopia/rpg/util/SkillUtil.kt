package net.mineutopia.rpg.util

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.mineutopia.rpg.cache.PlayerCache
import net.mineutopia.rpg.item.ItemData
import net.mineutopia.rpg.item.SkillBook
import net.mineutopia.rpg.skill.Skill
import net.mineutopia.rpg.skill.skills.ErrorSkill
import org.bukkit.entity.Player
import java.util.*

object SkillUtil {
    private val ctPlayerList: MutableList<UUID> = mutableListOf()

    fun setCT(player: Player, ms: Long) {
        GlobalScope.launch {
            ctPlayerList.add(player.uniqueId)
            delay(ms)
            ctPlayerList.remove(player.uniqueId)
        }
    }

    fun isCt(player: Player): Boolean {
        return ctPlayerList.contains(player.uniqueId)
    }

    fun getSelectedSkill(cache: PlayerCache): Skill {
        return when (cache.selected) {
            1 -> {
                ((ItemData.get(cache.skill1) ?: return ErrorSkill) as SkillBook).skill
            }
            2 -> {
                ((ItemData.get(cache.skill2) ?: return ErrorSkill) as SkillBook).skill
            }
            3 -> {
                ((ItemData.get(cache.skill3) ?: return ErrorSkill) as SkillBook).skill
            }
            4 -> {
                ((ItemData.get(cache.skill4) ?: return ErrorSkill) as SkillBook).skill
            }
            5 -> {
                ((ItemData.get(cache.skill5) ?: return ErrorSkill) as SkillBook).skill
            }
            else -> {
                ErrorSkill
            }
        }
    }
}