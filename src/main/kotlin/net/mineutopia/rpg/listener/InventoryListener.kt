package net.mineutopia.rpg.listener

import net.mineutopia.rpg.MineUtopia
import net.mineutopia.rpg.extension.customId
import net.mineutopia.rpg.gui.InventoryUI
import net.mineutopia.rpg.item.InvClickable
import net.mineutopia.rpg.item.InventoryItem
import net.mineutopia.rpg.item.ItemData
import net.mineutopia.rpg.item.SkillBook
import net.mineutopia.rpg.item.items.MenuItem
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent

class InventoryListener: Listener {
    private val plugin = MineUtopia.PLUGIN

    @EventHandler
    fun onInvClick(event: InventoryClickEvent) {
        val inventory = event.clickedInventory ?: return
        val name = event.view.title
        val currentItem = event.currentItem

        //あんまり効果ないけど表示バグ対策
        if (event.action == InventoryAction.HOTBAR_SWAP) {
            event.isCancelled = true
            return
        }
        //バックパック系アイテムの動作安全確保
        if (name == "BackPack") {
            val id = currentItem?.customId
            if (id != null) {
                val customItem = ItemData.get(id)
                if (customItem is InventoryItem) {
                    event.isCancelled = true
                    return
                }
            }
        }
        //スキル設定にスキル本以外を入れられないように
        if (name == "Skill") {
            val id = currentItem?.customId
            if (id != null) {
                val customItem = ItemData.get(id)
                if (customItem !is SkillBook) {
                    event.isCancelled = true
                }
            }
        }

        //InventoryUI実行
        if (inventory == event.view.topInventory) {
            when (val holder = event.inventory.holder) {
                is InventoryUI -> {
                    holder.getButton(event.slot)?.runAction(event)
                }
            }
        }

        //InvClickableItem実行
        if (currentItem?.customId != null) {
            val customItem = ItemData.get(currentItem.customId!!)
            if (customItem is InvClickable) {
                event.isCancelled = true
                customItem.onInvClick(event)
            }
        }
    }

    @EventHandler
    fun onInvOpen(event: InventoryOpenEvent) {
        val player = event.player
        //TODO 万が一アイテムが潜り込んだ時用の機能。念のため
        //TODO 開発中
        /*
        val offHand = player.inventory.itemInOffHand
        if (offHand.type != Material.AIR) {
            player.inventory.addItem(offHand)
        }
         */
        player.inventory.setItemInOffHand(MenuItem.toItemStack())
    }

    @EventHandler
    fun onInvClose(event: InventoryCloseEvent) {
        when (val holder = event.inventory.holder) {
            is InventoryUI -> {
                holder.onClose(event)
            }
        }
        //変なアイテムにインベントリがつかないよう少しチェック厳しめ
        val player = event.player
        val inventory = event.view.topInventory
        val itemStack = player.inventory.itemInMainHand
        val id = itemStack.customId ?: return
        val customItem = ItemData.get(id) ?: return
        if (customItem is InventoryItem) {
            when (val title = event.view.title) {
                "BackPack" -> {
                    customItem.setInv(itemStack, inventory, title)
                }
            }
        }
    }

    @EventHandler
    fun onPlayerSwapHandItems(event: PlayerSwapHandItemsEvent) {
        event.isCancelled = true
    }
}