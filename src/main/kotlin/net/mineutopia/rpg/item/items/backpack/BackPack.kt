package net.mineutopia.rpg.item.items.backpack

import net.mineutopia.rpg.extension.setHeadTexture
import net.mineutopia.rpg.extension.setNBTTag
import net.mineutopia.rpg.extension.setUnStackable
import net.mineutopia.rpg.item.*
import net.mineutopia.rpg.util.CustomItemUtil
import net.mineutopia.rpg.util.SerializeUtil
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

object BackPack: CustomItem, Clickable, HeadItem, InventoryItem {
    override val id: String = "backpack"
    override val material: Material = Material.PLAYER_HEAD
    override val displayName: String = "バックパック"
    override val lore: MutableList<String> = mutableListOf(
            "${ChatColor.GREEN}普通のバックパック",
            "${ChatColor.AQUA}収納量: 27"
    )
    override val price: Int = 0
    override val rarity: Rarity = Rarity.RARE

    override val texture: String = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDVjNmRjMmJiZjUxYzM2Y2ZjNzcxNDU4NWE2YTU2ODNlZjJiMTRkNDdkOGZmNzE0NjU0YTg5M2Y1ZGE2MjIifX19"

    override fun toItemStack(): ItemStack {
        return CustomItemUtil.customItemToItemStack(this).apply {
            this.setNBTTag("inventory", SerializeUtil.Inventory.toBase64(Bukkit.createInventory(null, 27, "BackPack"), "BackPack"))
            this.setHeadTexture(texture)
            this.setUnStackable()
        }
    }

    override fun onClick(event: PlayerInteractEvent) {
        if (event.action == Action.RIGHT_CLICK_BLOCK || event.action == Action.RIGHT_CLICK_AIR) {
            val player = event.player
            val item = event.item ?: return
            getInv(item)?.let { player.openInventory(it) }
        }
    }
}