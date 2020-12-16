package net.mineutopia.rpg.item.items.skillbook

import net.mineutopia.rpg.extension.setUnStackable
import net.mineutopia.rpg.item.Rarity
import net.mineutopia.rpg.item.SkillBook
import net.mineutopia.rpg.skill.Skill
import net.mineutopia.rpg.skill.skills.DebugSkill
import net.mineutopia.rpg.util.CustomItemUtil
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object DebugSkillBook: SkillBook {
    override val id: String = "debugskillbook"
    override val material: Material = Material.BOOK
    override val displayName: String = "デバッグ用_スキルブック"
    override val lore: MutableList<String> = mutableListOf("デバッグ用")
    override val price: Int = 0
    override val rarity: Rarity = Rarity.SYSTEM

    override val skill: Skill = DebugSkill

    override fun toItemStack(): ItemStack {
        return CustomItemUtil.customItemToItemStack(this).apply {
            setUnStackable()
        }
    }
}