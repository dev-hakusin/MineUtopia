package net.mineutopia.rpg.extension

import net.mineutopia.rpg.MineUtopia
import net.mineutopia.rpg.skill.DamageAttribution
import org.bukkit.NamespacedKey
import org.bukkit.entity.Entity
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType
import java.util.*

private val plugin = MineUtopia.PLUGIN

var Entity.customId: String?
    get() {
        return getStringNBT("customId")
    }
    set(value) {
        setNBTTag("customId", value ?: "unknown")
    }

var Entity.skillCT: Boolean
    get() {
        return getBooleanNBT("skillCT") ?: false
    }
    set(value) {
        setNBTTag("skillCT", value)
    }

var Entity.damageAttribution: DamageAttribution
    get() {
        return this.getStringNBT("damageattribution")?.let { DamageAttribution.valueOf(it) } ?: DamageAttribution.PHYSICAL
    }
    set(value) {
        this.setNBTTag("damageattribution", value.name)
    }

fun Entity.setNBTTag(key: String, value: Any) {
    when (value) {
        is Byte -> {
            persistentDataContainer.set(NamespacedKey(plugin, key), PersistentDataType.BYTE, value)
        }
        is ByteArray -> {
            persistentDataContainer.set(NamespacedKey(plugin, key), PersistentDataType.BYTE_ARRAY, value)
        }
        is Double -> {
            persistentDataContainer.set(NamespacedKey(plugin, key), PersistentDataType.DOUBLE, value)
        }
        is Float -> {
            persistentDataContainer.set(NamespacedKey(plugin, key), PersistentDataType.FLOAT, value)
        }
        is Int -> {
            persistentDataContainer.set(NamespacedKey(plugin, key), PersistentDataType.INTEGER, value)
        }
        is IntArray -> {
            persistentDataContainer.set(NamespacedKey(plugin, key), PersistentDataType.INTEGER_ARRAY, value)
        }
        is Long -> {
            persistentDataContainer.set(NamespacedKey(plugin, key), PersistentDataType.LONG, value)
        }
        is LongArray -> {
            persistentDataContainer.set(NamespacedKey(plugin, key), PersistentDataType.LONG_ARRAY, value)
        }
        is Short -> {
            persistentDataContainer.set(NamespacedKey(plugin, key), PersistentDataType.SHORT, value)
        }
        is String -> {
            persistentDataContainer.set(NamespacedKey(plugin, key), PersistentDataType.STRING, value)
        }
        is Boolean -> {
            persistentDataContainer.set(NamespacedKey(plugin, key), PersistentDataType.STRING, (value.toString()))
        }
        is UUID -> {
            persistentDataContainer.set(NamespacedKey(plugin, key), PersistentDataType.STRING, (value.toString()))
        }
        //何に使うのか不明
        is PersistentDataContainer -> {
            persistentDataContainer.set(NamespacedKey(plugin, key), PersistentDataType.TAG_CONTAINER, value)
        }
        else -> return
    }
}

private fun getNBT(entity: Entity, key: String, persistentDataType: PersistentDataType<out Any, out Any>): Any? {
    return entity.persistentDataContainer.get(NamespacedKey(plugin, key), persistentDataType)
}

fun Entity.getByteNBT(key: String): Byte? {
    return (getNBT(this, key, PersistentDataType.BYTE) ?: return null) as Byte
}

fun Entity.getByteArrayNBT(key: String): ByteArray? {
    return (getNBT(this, key, PersistentDataType.BYTE_ARRAY) ?: return null) as ByteArray
}

fun Entity.getDoubleNBT(key: String): Double? {
    return (getNBT(this, key, PersistentDataType.DOUBLE) ?: return null) as Double
}

fun Entity.getFloatNBT(key: String): Float? {
    return (getNBT(this, key, PersistentDataType.FLOAT) ?: return null) as Float
}

fun Entity.getIntNBT(key: String): Int? {
    return (getNBT(this, key, PersistentDataType.INTEGER) ?: return null) as Int
}

fun Entity.getIntArrayNBT(key: String): IntArray? {
    return (getNBT(this, key, PersistentDataType.INTEGER_ARRAY) ?: return null) as IntArray
}

fun Entity.getLongNBT(key: String): Long? {
    return (getNBT(this, key, PersistentDataType.LONG) ?: return null) as Long
}

fun Entity.getLongArrayNBT(key: String): LongArray? {
    return (getNBT(this, key, PersistentDataType.LONG_ARRAY) ?: return null) as LongArray
}

fun Entity.getShortNBT(key: String): Short? {
    return (getNBT(this, key, PersistentDataType.SHORT) ?: return null) as Short
}

fun Entity.getStringNBT(key: String): String? {
    return (getNBT(this, key, PersistentDataType.STRING) ?: return null) as String
}

fun Entity.getBooleanNBT(key: String): Boolean? {
    return when ((getNBT(this, key, PersistentDataType.STRING) ?: return null) as String) {
        "true" -> true
        "false" -> false
        else -> null
    }
}

fun Entity.getUUIDNBT(key: String): UUID? {
    try {
        return UUID.fromString(((getNBT(this, key, PersistentDataType.STRING) ?: return null) as String))
    }catch (e: Exception) {
        return null
    }
}


fun Entity.removeNBT(key: String) {
    persistentDataContainer.remove(NamespacedKey(plugin, key))
}