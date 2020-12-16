package net.mineutopia.rpg.mob

interface CustomMob {
    val id: String
    val basename: String

    val stats: MobStats

    fun register() {
        MobData.register(this)
    }
}