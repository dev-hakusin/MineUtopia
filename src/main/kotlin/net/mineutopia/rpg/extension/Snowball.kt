package net.mineutopia.rpg.extension

import org.bukkit.entity.Snowball

var Snowball.damage: Double
    get() {
        return this.getDoubleNBT("damage") ?: 0.0
    }
    set(value) {
        this.setNBTTag("damage", value)
    }