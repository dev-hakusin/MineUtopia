package net.mineutopia.rpg.item.items

import net.mineutopia.rpg.extension.customId
import net.mineutopia.rpg.extension.displayName
import net.mineutopia.rpg.extension.lore
import net.mineutopia.rpg.extension.setUnStackable
import net.mineutopia.rpg.gui.menus.MainMenu
import net.mineutopia.rpg.item.CustomItem
import net.mineutopia.rpg.item.InvClickable
import net.mineutopia.rpg.item.Rarity
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

object MenuItem: CustomItem, InvClickable {
    override val id: String = "menuitem"
    override val material: Material = Material.COMPASS
    override val displayName: String = "${ChatColor.GREEN}メインメニュー"
    override val lore: MutableList<String> = mutableListOf(
            "${ChatColor.RESET}${ChatColor.GRAY}クリックで開く"
    )
    override val price: Int = 0
    override val rarity: Rarity = Rarity.SYSTEM

    override fun toItemStack(): ItemStack {
       return ItemStack(this.material).apply {
            this.customId = id
            this.displayName = MenuItem.displayName
            this.lore = MenuItem.lore
           this.setUnStackable()
        }
    }

    override fun onInvClick(event: InventoryClickEvent) {
        MainMenu.open(event.whoClicked as Player)
    }
}