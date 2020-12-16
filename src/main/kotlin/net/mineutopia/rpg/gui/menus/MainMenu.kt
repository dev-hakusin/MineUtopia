package net.mineutopia.rpg.gui.menus

import net.mineutopia.rpg.extension.displayName
import net.mineutopia.rpg.extension.lore
import net.mineutopia.rpg.gui.Button
import net.mineutopia.rpg.gui.InventoryUI
import net.mineutopia.rpg.sound.CustomSound
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta

object MainMenu : InventoryUI() {
    override val size = 54

    override fun getTitle(player: Player): String {
        return "Main Menu"
    }

    override fun open(player: Player) {
        val inv = toInventory(player).apply {
            setItem(13, this.getItem(13).apply {
                val meta = this?.itemMeta
                (meta as SkullMeta).owningPlayer = player
                this?.itemMeta = meta
            })
        }
        soundOpen.play(player)
        player.openInventory(inv)
    }

    init {
        //敷き詰め
        for (i in 0..54) {
            registerButton(i, object : Button {
                override val material: Material = Material.AIR
                override val buttonName: String = ""
                override val buttonLore: MutableList<String> = mutableListOf()

                override fun runAction(event: InventoryClickEvent) {
                    event.isCancelled = true
                }

                override fun toItemStack(player: Player): ItemStack {
                    return ItemStack(material)
                }
            })
        }

        for (i in 0..9) {
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

        registerButton(13, object : Button {
            override val material: Material = Material.PLAYER_HEAD
            override val buttonName: String = "${ChatColor.GREEN}ステータス"
            override val buttonLore: MutableList<String> = mutableListOf()

            override fun runAction(event: InventoryClickEvent) {
                event.isCancelled = true
                //TODO 詳細ステータスGUI
            }

            override fun toItemStack(player: Player): ItemStack {
                return ItemStack(material).apply {
                    displayName = buttonName
                    lore = buttonLore
                }
            }

        })

        for (i in 17..18) {
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

        registerButton(19, object : Button {
            override val material: Material = Material.NAME_TAG
            override val buttonName: String = "${ChatColor.GREEN}パーティー"
            override val buttonLore: MutableList<String> = mutableListOf("${ChatColor.RESET}パーティーを管理")

            override fun runAction(event: InventoryClickEvent) {
                event.isCancelled = true
                //TODO 詳細GUI
            }

            override fun toItemStack(player: Player): ItemStack {
                return ItemStack(material).apply {
                    displayName = buttonName
                    lore = buttonLore
                }
            }

        })

        for (i in 26..27) {
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

        registerButton(29, object : Button {
            override val material: Material = Material.OAK_DOOR
            override val buttonName: String = "${ChatColor.GREEN}テレポート"
            override val buttonLore: MutableList<String> = mutableListOf()

            override fun runAction(event: InventoryClickEvent) {
                event.isCancelled = true
                //TODO 詳細GUI
            }

            override fun toItemStack(player: Player): ItemStack {
                return ItemStack(material).apply {
                    displayName = buttonName
                    lore = buttonLore
                }
            }

        })

        registerButton(33, object : Button {
            override val material: Material = Material.SUNFLOWER
            override val buttonName: String = "${ChatColor.GREEN}アクセサリー"
            override val buttonLore: MutableList<String> = mutableListOf()

            override fun runAction(event: InventoryClickEvent) {
                event.isCancelled = true
                //TODO 詳細GUI
            }

            override fun toItemStack(player: Player): ItemStack {
                return ItemStack(material).apply {
                    displayName = buttonName
                    lore = buttonLore
                }
            }

        })

        for (i in 35..36) {
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

        registerButton(37, object : Button {
            override val material: Material = Material.ENDER_CHEST
            override val buttonName: String = "${ChatColor.RESET}チェストを開く"
            override val buttonLore: MutableList<String> = mutableListOf()

            override fun runAction(event: InventoryClickEvent) {
                event.isCancelled = true
                val player = event.whoClicked as Player

                CustomSound(Sound.BLOCK_ENDER_CHEST_OPEN, SoundCategory.BLOCKS,1f, 1f).play(player)
                player.openInventory(player.enderChest)
            }

            override fun toItemStack(player: Player): ItemStack {
                return ItemStack(material).apply {
                    displayName = buttonName
                    lore = buttonLore
                }
            }

        })

        registerButton(39, object : Button {
            override val material: Material = Material.BIRCH_SIGN
            override val buttonName: String = "${ChatColor.GREEN}クエスト"
            override val buttonLore: MutableList<String> = mutableListOf()

            override fun runAction(event: InventoryClickEvent) {
                event.isCancelled = true
                //TODO 詳細GUI
            }

            override fun toItemStack(player: Player): ItemStack {
                return ItemStack(material).apply {
                    displayName = buttonName
                    lore = buttonLore
                }
            }

        })

        registerButton(43, object : Button {
            override val material: Material = Material.BOOK
            override val buttonName: String = "${ChatColor.GREEN}スキル設定"
            override val buttonLore: MutableList<String> = mutableListOf()

            override fun runAction(event: InventoryClickEvent) {
                event.isCancelled = true
                MagicSkillMenu.open(event.whoClicked as Player)
            }

            override fun toItemStack(player: Player): ItemStack {
                return ItemStack(material).apply {
                    val meta = this.itemMeta
                    meta?.addEnchant(Enchantment.LUCK, 1, true)
                    meta?.addItemFlags(ItemFlag.HIDE_ENCHANTS)
                    this.itemMeta = meta
                    displayName = buttonName
                    lore = buttonLore
                }
            }

        })
        
        for (i in 44..54) {
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