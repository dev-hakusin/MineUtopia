package net.mineutopia.rpg.util

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import net.mineutopia.rpg.MineUtopia
import net.mineutopia.rpg.cache.PlayerCacheMemory
import org.bukkit.ChatColor
import org.bukkit.entity.*

object PlayerUtil {
    private val plugin = MineUtopia.PLUGIN

    private val deadPlayerList: MutableList<Player> = mutableListOf()

    fun isDead(player: Player): Boolean {
        return deadPlayerList.contains(player)
    }

    fun formatPlayerListName(player: Player) {
        val cache = PlayerCacheMemory.get(player.uniqueId)
        val job = cache.playerJob
        player.setPlayerListName("${job.color}[${cache.level}]${ChatColor.RESET} ${player.name}")
    }

    fun onPlayerDead(deadPlayer: Player) {
        //TODO パーティーメンバーにのみ送信
        deadPlayerList.add(deadPlayer)

        NpcUtil.spawnDeadPlayer(deadPlayer)

        (deadPlayer as LivingEntity).isInvulnerable = true
        deadPlayer.isInvisible = true
        hidePlayer(deadPlayer)

        deadPlayer.sendTitle("${ChatColor.RED}${ChatColor.BOLD}YOU DEAD!", "15秒で自動リスポーンします", 20, 100, 20)
        val stand = deadPlayer.world.spawnEntity(deadPlayer.location, EntityType.ARMOR_STAND) as ArmorStand
        stand.isVisible = false
        stand.isInvulnerable = true
        stand.isSmall = true

        stand.addPassenger(deadPlayer)

        deadPlayer.getNearbyEntities(20.0, 20.0, 20.0).forEach {
            if (it is Mob) {
                if (it.target == deadPlayer) {
                    it.target = null
                }
            }
        }

        deadPlayer.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent().apply {
            text = "Sneak to Respawn."
        })
        GlobalScope.launch {
            delay(15000)
            
            if (deadPlayerList.contains(deadPlayer)) {
                //TODO 蘇生
                //TODO アマスタ削除
                plugin.logger.info("蘇生!")
            }
        }
    }

    fun onDeadPlayerReSpawn(player: Player) {
        deadPlayerList.remove(player)

        player.isInvulnerable = false
        player.isInvisible = false

        NpcUtil.removeDeadPlayer(player)
        showPlayer(player)
        //TODO プレイヤーをテレポート
        player.damage(100.0)
    }

    private fun hidePlayer(player: Player) {
        plugin.server.onlinePlayers.forEach {
            it.hidePlayer(plugin, player)
        }
    }

    private fun showPlayer(player: Player) {
        plugin.server.onlinePlayers.forEach {
            it.showPlayer(plugin, player)
        }
    }
}