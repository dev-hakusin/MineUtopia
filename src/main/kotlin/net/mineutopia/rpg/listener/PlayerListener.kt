package net.mineutopia.rpg.listener

import net.mineutopia.rpg.MineUtopia
import net.mineutopia.rpg.cache.PlayerCacheMemory
import net.mineutopia.rpg.extension.customId
import net.mineutopia.rpg.extension.isClickFlag
import net.mineutopia.rpg.extension.setClickFlag
import net.mineutopia.rpg.item.Clickable
import net.mineutopia.rpg.item.ItemData
import net.mineutopia.rpg.item.items.MenuItem
import net.mineutopia.rpg.util.PlayerUtil
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerListener: Listener {
    private val plugin = MineUtopia.PLUGIN

    @EventHandler
    fun onPlayerPreLoginAsync(event: AsyncPlayerPreLoginEvent) {
        //ログインしようとするとき、データをロードする
        PlayerCacheMemory.add(event.uniqueId, event.name)
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        if (!PlayerCacheMemory.contains(player.uniqueId)) {
            player.kickPlayer("プレイヤーデータを読み込んでいます。もうしばらくお待ちください")
            return
        }

        player.inventory.setItemInOffHand(MenuItem.toItemStack())
        PlayerUtil.formatPlayerListName(player)
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        //ログアウト時にデータ保存
        val player = event.player
        if (!PlayerCacheMemory.contains(player.uniqueId)) {
            return
        }
        PlayerCacheMemory.saveThenRemoved(player.uniqueId, true)
    }

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        //2回反応しないように
        if (player.isClickFlag()) {
            return
        }
        player.setClickFlag()
        val item = player.inventory.itemInMainHand
        val customItem = ItemData.get(item.customId ?: return) ?: return
        if (customItem is Clickable) {
            customItem.onClick(event)
        }
    }
}