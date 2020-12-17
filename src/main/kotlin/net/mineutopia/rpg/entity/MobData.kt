package net.mineutopia.rpg.entity

object MobData {
    private val map: MutableMap<String, CustomMob> = mutableMapOf()

    fun register(customMob: CustomMob) {
        map[customMob.id] = customMob
    }

    fun get(id: String): CustomMob? {
        return map[id]
    }

    fun getMap(): MutableMap<String, CustomMob> {
        return map
    }
}