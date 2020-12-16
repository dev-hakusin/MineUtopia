package net.mineutopia.rpg.extension

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.mineutopia.rpg.MineUtopia
import net.mineutopia.rpg.util.SkillUtil
import org.bukkit.entity.Player
import org.bukkit.metadata.FixedMetadataValue
import java.util.*

private val plugin = MineUtopia.PLUGIN

var Player.partyId: UUID?
    get() {
        return getUUIDNBT("party")
    }
    set(value) {
        setNBTTag("party", value ?: return)
    }

val Player.isSkillCT: Boolean
    get() {
        return SkillUtil.isCt(this)
    }

fun Player.setSkillCT(ms: Long) {
    SkillUtil.setCT(this, ms)
}

fun Player.isClickFlag(): Boolean {
    return try {
        this.getMetadata("InteractFlag")[0].value() as Boolean
    } catch (e: Exception) {
        false
    }
}

fun Player.setClickFlag() {
    this.setMetadata("InteractFlag", FixedMetadataValue(plugin, true))
    GlobalScope.launch {
        delay(10)
        setMetadata("InteractFlag", FixedMetadataValue(plugin, false))
    }
}