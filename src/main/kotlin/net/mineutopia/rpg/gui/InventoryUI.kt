package net.mineutopia.rpg.gui

import net.mineutopia.rpg.sound.CustomSound
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

abstract class InventoryUI : InventoryHolder {
    private val map: MutableMap<Int, Button> = mutableMapOf()

    val type = InventoryType.CHEST

    open val size by lazy {
        type.defaultSize
    }

    open val soundOpen = CustomSound(Sound.BLOCK_DISPENSER_FAIL, SoundCategory.BLOCKS, 1f, 1f)

    open val soundClose = CustomSound(Sound.BLOCK_FENCE_GATE_CLOSE, SoundCategory.BLOCKS, 1f ,1f)

    protected fun getButtons() = map.toMap()

    abstract fun getTitle(player: Player): String

    protected fun registerButton(slot: Int, button: Button) {
        map[slot] = button
    }

    fun getButton(slot: Int): Button? {
        return map[slot]
    }

    open fun open(player: Player) {
        soundOpen.play(player)
        player.openInventory(toInventory(player))
    }

    open fun reOpen(player: Player) {
        player.openInventory(toInventory(player))
    }

    open fun close(player: Player) {
        player.closeInventory()
        soundClose.play(player)
    }

    open fun onClose(event: InventoryCloseEvent) {
    }

    protected open fun toInventory(player: Player): Inventory {
        val inv = Bukkit.createInventory(this, size, getTitle(player))
        (0 until size).forEach {
            inv.setItem(it, (map[it] ?: return@forEach).toItemStack(player))
        }
        return inv
    }

    override fun getInventory(): Inventory {
        return Bukkit.createInventory(null, 9)
    }

}