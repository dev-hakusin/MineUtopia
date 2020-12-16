package net.mineutopia.rpg.item.items.weapon.wand

import net.mineutopia.rpg.extension.setUnStackable
import net.mineutopia.rpg.item.Clickable
import net.mineutopia.rpg.item.CustomItem
import net.mineutopia.rpg.item.Rarity
import net.mineutopia.rpg.item.Weapon
import net.mineutopia.rpg.util.CustomItemUtil
import net.mineutopia.rpg.util.WandUtil
import org.bukkit.Material
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

object DebugWand: CustomItem, Clickable, Weapon {
    override val id: String = "debugwand"
    override val material: Material = Material.STICK
    override val displayName: String = "デバッグ用"
    override val lore: MutableList<String> = mutableListOf("デバッグ用の杖")
    override val price: Int = 0
    override val rarity: Rarity = Rarity.SYSTEM

    override val strength: Double = 10.0

    override fun toItemStack(): ItemStack {
        return CustomItemUtil.customItemToItemStack(this).apply {
            setUnStackable()
        }
    }

    override fun onClick(event: PlayerInteractEvent) {
       WandUtil.useWand(event, this)
    }
}