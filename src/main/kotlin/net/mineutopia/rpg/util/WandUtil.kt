package net.mineutopia.rpg.util

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import net.mineutopia.rpg.cache.PlayerCacheMemory
import net.mineutopia.rpg.extension.isSkillCT
import net.mineutopia.rpg.extension.setSkillCT
import net.mineutopia.rpg.item.Weapon
import org.bukkit.Sound
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

object WandUtil {
    fun useWand(event: PlayerInteractEvent, wand: Weapon) {
        val player = event.player
        val cache = PlayerCacheMemory.get(player.uniqueId)
        when (event.action) {
            Action.LEFT_CLICK_AIR, Action.LEFT_CLICK_BLOCK -> {
                if (cache.selected < 5) {
                    cache.selected += 1
                } else {
                    cache.selected = 1
                }
                PlayerCacheMemory.set(player.uniqueId, cache)

                player.spigot().sendMessage(
                        ChatMessageType.ACTION_BAR,
                        //varargに配列を渡す。スプレッド演算子っていうらしい
                        *TextComponent.fromLegacyText("${ChatColor.GREEN}スキルを変更 ${ChatColor.AQUA}${ChatColor.BOLD}現在: ${cache.selected}")
                )
            }
            Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK -> {
                if (player.isSkillCT) {
                    player.playSound(player.location, Sound.BLOCK_DISPENSER_FAIL, 1f, 1f)
                    player.sendMessage("${ChatColor.RED}クールタイム中です")
                    return
                }
                val skill = SkillUtil.getSelectedSkill(cache)
                //TODO MP消費
                player.setSkillCT(skill.ct)
                skill.run(player, wand)
            }
            else -> return
        }
    }
}