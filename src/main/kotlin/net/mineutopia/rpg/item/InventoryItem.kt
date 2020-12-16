package net.mineutopia.rpg.item

import net.mineutopia.rpg.extension.getStringNBT
import net.mineutopia.rpg.extension.setNBTTag
import net.mineutopia.rpg.util.SerializeUtil
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

interface InventoryItem {
    fun getInv (item: ItemStack): Inventory? {
        val data = item.getStringNBT("inventory")
        return data?.let { SerializeUtil.Inventory.fromBase64(it) }
    }

    fun setInv (item: ItemStack, inventory: Inventory, invName: String) {
        item.setNBTTag("inventory", SerializeUtil.Inventory.toBase64(inventory, invName))
    }
}