package net.mineutopia.rpg.util

import org.bukkit.Bukkit
import org.bukkit.inventory.ItemStack
import org.bukkit.util.io.BukkitObjectInputStream
import org.bukkit.util.io.BukkitObjectOutputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*

object SerializeUtil {
    object Inventory {
        @Throws(IOException::class)
        fun fromBase64(inventoryData: String): org.bukkit.inventory.Inventory? {
            val stream = ByteArrayInputStream(Base64.getDecoder().decode(inventoryData))
            val data = BukkitObjectInputStream(stream)
            val inventory = Bukkit.createInventory(null, data.readInt(), data.readObject().toString())
            for (i in 0 until inventory.size) {
                try {
                    inventory.setItem(i, data.readObject() as ItemStack)
                } catch (e: TypeCastException) {
                    continue
                } catch (e: NullPointerException) {
                    continue
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            data.close()
            return inventory
        }

        fun toBase64(inventory: org.bukkit.inventory.Inventory, inventoryName: String): String {
            try {
                val str = ByteArrayOutputStream()
                val data = BukkitObjectOutputStream(str)
                data.writeInt(inventory.size)
                data.writeObject(inventoryName)
                for (i in 0 until inventory.size) {
                    data.writeObject(inventory.getItem(i))
                }
                data.close()
                return Base64.getEncoder().encodeToString(str.toByteArray())
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }
    }
}