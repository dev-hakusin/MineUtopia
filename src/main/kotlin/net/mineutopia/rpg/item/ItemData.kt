package net.mineutopia.rpg.item

object ItemData {
    private val map: MutableMap<String, CustomItem> = mutableMapOf()

    fun register(customItem: CustomItem) {
        map[customItem.id] = customItem
    }

    fun get(id: String): CustomItem? {
        return map[id]
    }

    fun getMap(): MutableMap<String, CustomItem> {
        return map
    }
}