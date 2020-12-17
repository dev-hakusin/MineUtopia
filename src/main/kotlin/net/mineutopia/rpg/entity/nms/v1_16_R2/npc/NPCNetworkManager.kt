package net.mineutopia.rpg.entity.nms.v1_16_R2.npc

import net.minecraft.server.v1_16_R2.NetworkManager

import net.minecraft.server.v1_16_R2.EnumProtocolDirection
import java.lang.IllegalArgumentException
import java.lang.reflect.Field
import java.net.SocketAddress

class NPCNetworkManager : NetworkManager(EnumProtocolDirection.CLIENTBOUND) {
    @Throws(NoSuchFieldException::class, SecurityException::class)
    private fun getField(string: String): Field {
        val f: Field = NetworkManager::class.java.getDeclaredField(string)
        f.isAccessible = true
        return f
    }

    init {
        try {
            val channel: Field = getField("channel")
            val address: Field = getField("socketAddress")
            channel.set(this, NPCChannel())
            address.set(this, object : SocketAddress() {
                private val serialVersionUID = 7909117673726728027L
            })
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}