package net.mineutopia.rpg.extension

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import net.mineutopia.rpg.MineUtopia
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType
import java.util.*

private val plugin = MineUtopia.PLUGIN

var ItemStack.displayName: String
    get() {
        return try {
            itemMeta!!.displayName
        }catch (e: Exception) {
            ""
        }
    }
    set(value) {
        itemMeta = itemMeta?.also { meta ->
            meta.setDisplayName(value)
        }
    }

var ItemStack.lore: MutableList<String>
    get() {
        return try {
            itemMeta!!.lore!!.toMutableList()
        }catch (e: Exception) {
            mutableListOf()
        }
    }
    set(value) {
        itemMeta = itemMeta?.also {
            it.lore = value
        }
    }

var ItemStack.customId: String?
    get() {
        return getStringNBT("customId")
    }
    set(value) {
        setNBTTag("customId", value?.toLowerCase() ?: "unknown")
    }

fun ItemStack.setUnStackable() {
    setNBTTag("unStackable", UUID.randomUUID())
}

fun ItemStack.setHeadTexture(textureValue: String) {
    if (this.type == Material.PLAYER_HEAD) {
        val meta = itemMeta as SkullMeta
        val dummyProfile = GameProfile(UUID.randomUUID(), null)
        dummyProfile.properties.put("textures", Property("textures", textureValue))
        meta.setFieldValue("profile", dummyProfile)
        itemMeta = meta
    }
}

fun ItemStack.setNBTTag(map: MutableMap<String, Any>) {
    map.forEach {
        setNBTTag(it.key, it.value)
    }
}

fun ItemStack.setNBTTag(key: String, value: Any) {
    when (value) {
        is Byte -> itemMeta = itemMeta?.also { meta ->
            meta.persistentDataContainer.set(NamespacedKey(plugin, key), PersistentDataType.BYTE, value)
        }
        is ByteArray -> itemMeta = itemMeta?.also { meta ->
            meta.persistentDataContainer.set(NamespacedKey(plugin, key), PersistentDataType.BYTE_ARRAY, value)
        }
        is Double -> itemMeta = itemMeta?.also { meta ->
            meta.persistentDataContainer.set(NamespacedKey(plugin, key), PersistentDataType.DOUBLE, value)
        }
        is Float -> itemMeta = itemMeta?.also {meta ->
            meta.persistentDataContainer.set(NamespacedKey(plugin, key), PersistentDataType.FLOAT, value)
        }
        is Int -> itemMeta = itemMeta?.also { meta ->
            meta.persistentDataContainer.set(NamespacedKey(plugin, key), PersistentDataType.INTEGER, value)
        }
        is IntArray -> itemMeta = itemMeta?.also { meta ->
            meta.persistentDataContainer.set(NamespacedKey(plugin, key), PersistentDataType.INTEGER_ARRAY, value)
        }
        is Long -> itemMeta = itemMeta?.also { meta ->
            meta.persistentDataContainer.set(NamespacedKey(plugin, key), PersistentDataType.LONG, value)
        }
        is LongArray -> itemMeta = itemMeta?.also { meta ->
            meta.persistentDataContainer.set(NamespacedKey(plugin, key), PersistentDataType.LONG_ARRAY, value)
        }
        is Short -> itemMeta = itemMeta?.also { meta ->
            meta.persistentDataContainer.set(NamespacedKey(plugin, key), PersistentDataType.SHORT, value)
        }
        is String -> itemMeta = itemMeta?.also { meta ->
            meta.persistentDataContainer.set(NamespacedKey(plugin, key), PersistentDataType.STRING, value)
        }
        is Boolean -> itemMeta = itemMeta?.also { meta ->
            meta.persistentDataContainer.set(NamespacedKey(plugin, key), PersistentDataType.STRING, (value.toString()))
        }
        is UUID -> itemMeta = itemMeta?.also { meta ->
            meta.persistentDataContainer.set(NamespacedKey(plugin, key), PersistentDataType.STRING, (value.toString()))
        }
        //何に使うのか不明
        is PersistentDataContainer -> itemMeta = itemMeta?.also { meta ->
            meta.persistentDataContainer.set(NamespacedKey(plugin, key), PersistentDataType.TAG_CONTAINER, value)
        }
        else -> return
    }
}

private fun getNBT(itemStack: ItemStack, key: String, persistentDataType: PersistentDataType<out Any, out Any>): Any? {
    return itemStack.itemMeta?.persistentDataContainer?.get(NamespacedKey(plugin, key), persistentDataType)
}

fun ItemStack.getByteNBT(key: String): Byte? {
    return (getNBT(this, key, PersistentDataType.BYTE) ?: return null) as Byte
}

fun ItemStack.getByteArrayNBT(key: String): ByteArray? {
    return (getNBT(this, key, PersistentDataType.BYTE_ARRAY) ?: return null) as ByteArray
}

fun ItemStack.getDoubleNBT(key: String): Double? {
    return (getNBT(this, key, PersistentDataType.DOUBLE) ?: return null) as Double
}

fun ItemStack.getFloatNBT(key: String): Float? {
    return (getNBT(this, key, PersistentDataType.FLOAT) ?: return null) as Float
}

fun ItemStack.getIntNBT(key: String): Int? {
    return (getNBT(this, key, PersistentDataType.INTEGER) ?: return null) as Int
}

fun ItemStack.getIntArrayNBT(key: String): IntArray? {
    return (getNBT(this, key, PersistentDataType.INTEGER_ARRAY) ?: return null) as IntArray
}

fun ItemStack.getLongNBT(key: String): Long? {
    return (getNBT(this, key, PersistentDataType.LONG) ?: return null) as Long
}

fun ItemStack.getLongArrayNBT(key: String): LongArray? {
    return (getNBT(this, key, PersistentDataType.LONG_ARRAY) ?: return null) as LongArray
}

fun ItemStack.getShortNBT(key: String): Short? {
    return (getNBT(this, key, PersistentDataType.SHORT) ?: return null) as Short
}

fun ItemStack.getStringNBT(key: String): String? {
    return (getNBT(this, key, PersistentDataType.STRING) ?: return null) as String
}

fun ItemStack.getBooleanNBT(key: String): Boolean? {
    return when ((getNBT(this, key, PersistentDataType.STRING) ?: return null) as String) {
        "true" -> true
        "false" -> false
        else -> null
    }
}

fun ItemStack.getUUIDNBT(key: String): UUID? {
    try {
        return UUID.fromString(((getNBT(this, key, PersistentDataType.STRING) ?: return null) as String))
    }catch (e: Exception) {
        return null
    }
}


fun ItemStack.removeNBT(key: String) {
    itemMeta?.persistentDataContainer?.remove(NamespacedKey(plugin, key)) ?: return
}