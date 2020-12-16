package net.mineutopia.rpg.gui.menus

import net.mineutopia.rpg.cache.PlayerCacheMemory
import net.mineutopia.rpg.extension.customId
import net.mineutopia.rpg.extension.displayName
import net.mineutopia.rpg.extension.lore
import net.mineutopia.rpg.gui.Button
import net.mineutopia.rpg.gui.InventoryUI
import net.mineutopia.rpg.item.ItemData
import net.mineutopia.rpg.item.SkillBook
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack

object MagicSkillMenu : InventoryUI() {
    override val size: Int = 27

    override fun getTitle(player: Player): String {
        return "Skill"
    }

    override fun onClose(event: InventoryCloseEvent) {
        //TODO スキル本以外を入れられた時の対応
        val inventory = event.inventory
        val player = event.player as Player

        val slot0 = inventory.getItem(11)
        val slot1 = inventory.getItem(12)
        val slot2 = inventory.getItem(13)
        val slot3 = inventory.getItem(14)
        val slot4 = inventory.getItem(15)

        PlayerCacheMemory.get(player.uniqueId).apply {
            skill1 = skillbookIdGetter(slot0)
            skill2 = skillbookIdGetter(slot1)
            skill3 = skillbookIdGetter(slot2)
            skill4 = skillbookIdGetter(slot3)
            skill5 = skillbookIdGetter(slot4)
        }
    }

    private fun skillbookIdGetter(itemStack: ItemStack?): String {
        val id = itemStack?.customId ?: return "unknown"
        val customItem = ItemData.get(id) ?: return "unknown"
        if (customItem is SkillBook) {
            return customItem.id
        }
        return "unknown"
    }

    override fun open(player: Player) {
        val cache = PlayerCacheMemory.get(player.uniqueId)
        val selected = cache.selected
        val slot = selected + 19
        val inv = toInventory(player).apply {
            setItem(11, getSkillBook(cache.skill1)?.toItemStack() ?: ItemStack(Material.AIR))
            setItem(12, getSkillBook(cache.skill2)?.toItemStack() ?: ItemStack(Material.AIR))
            setItem(13, getSkillBook(cache.skill3)?.toItemStack() ?: ItemStack(Material.AIR))
            setItem(14, getSkillBook(cache.skill4)?.toItemStack() ?: ItemStack(Material.AIR))
            setItem(15, getSkillBook(cache.skill5)?.toItemStack() ?: ItemStack(Material.AIR))

            setItem(slot, ItemStack(Material.RED_STAINED_GLASS_PANE).apply {
                displayName = "${ChatColor.RED}選択中"
            })
        }

        player.openInventory(inv)
    }

    private fun getSkillBook(id: String): SkillBook? {
        val item = ItemData.get(id) ?: return null
        if (item !is SkillBook) return null
        return item
    }

    init {
        for (i in 0..10) {
            registerButton(i, object : Button {
                override val material: Material = Material.BLACK_STAINED_GLASS_PANE
                override val buttonName: String = "${ChatColor.RESET}"
                override val buttonLore: MutableList<String> = mutableListOf()

                override fun runAction(event: InventoryClickEvent) {
                    event.isCancelled = true
                }

                override fun toItemStack(player: Player): ItemStack {
                    return ItemStack(material).apply {
                        displayName = buttonName
                        lore = buttonLore
                    }
                }

            })
        }

        for (i in 16..27) {
            registerButton(i, object : Button {
                override val material: Material = Material.BLACK_STAINED_GLASS_PANE
                override val buttonName: String = "${ChatColor.RESET}"
                override val buttonLore: MutableList<String> = mutableListOf()

                override fun runAction(event: InventoryClickEvent) {
                    event.isCancelled = true
                }

                override fun toItemStack(player: Player): ItemStack {
                    return ItemStack(material).apply {
                        displayName = buttonName
                        lore = buttonLore
                    }
                }

            })
        }
    }
}