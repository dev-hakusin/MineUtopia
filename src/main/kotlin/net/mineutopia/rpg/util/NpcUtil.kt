package net.mineutopia.rpg.util

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.PropertyMap
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.minecraft.server.v1_16_R2.*
import net.mineutopia.rpg.MineUtopia
import net.mineutopia.rpg.entity.nms.v1_16_R2.npc.NPCNetworkManager
import net.mineutopia.rpg.extension.setFieldValue
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_16_R2.CraftServer
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.util.*

object NpcUtil {
    private val plugin = MineUtopia.PLUGIN

    private lateinit var manager: NPCNetworkManager

    private val deadPlayerMap: MutableMap<UUID, DeadPlayer> = mutableMapOf()
    private data class DeadPlayer(val uniqueId: UUID, val id: Int)

    fun registerManager() {
        manager = NPCNetworkManager()
    }
    fun getManager(): NPCNetworkManager {
        return manager
    }

    fun spawnDeadPlayer(deadPlayer: Player) {
        //ワールドデータ?が悪さをするのかNPCが召喚直後に消える事象を確認。調査中。
        val server = (Bukkit.getServer() as CraftServer).server as MinecraftServer
        val world = (Bukkit.getServer().worlds[0] as CraftWorld).handle

        val gameProfile: GameProfile = (deadPlayer as CraftPlayer).profile

        val uuid = UUID.randomUUID()

        val uuidField = gameProfile.javaClass.getDeclaredField("id")
        uuidField.isAccessible = true
        val field = Field::class.java.getDeclaredField("modifiers")
        field.isAccessible = true
        field.setInt(uuidField, uuidField.modifiers and Modifier.PRIVATE.inv() and Modifier.FINAL.inv())
        uuidField.set(gameProfile, uuid)

        val id = getSafetyEntityID()
        deadPlayerMap[deadPlayer.uniqueId] = DeadPlayer(uuid, id)

        val fakePlayer = EntityPlayer(server, world, gameProfile, PlayerInteractManager(world))
        val loc = deadPlayer.location
        fakePlayer.setLocation(loc.x, loc.y, loc.z, loc.pitch, loc.yaw)

        val watcher = fakePlayer.dataWatcher
        val b = 0x40.toByte()

        watcher.set(DataWatcherObject(16, DataWatcherRegistry.a), b)
        fakePlayer.entitySleep(BlockPosition(loc.x, loc.y, loc.z))

        plugin.server.onlinePlayers.forEach {
            val npcId: Int = deadPlayerMap[deadPlayer.uniqueId]?.id ?: return@forEach

            val connection = (it as CraftPlayer).handle.playerConnection

            connection.sendPacket(PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, fakePlayer))

            val p = PacketPlayOutNamedEntitySpawn(fakePlayer)
            p.setFieldValue("a", npcId)
            connection.sendPacket(p)

            connection.sendPacket(PacketPlayOutEntityMetadata(npcId, watcher, false))

            GlobalScope.launch {
                delay(500)
                connection.sendPacket(PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, fakePlayer))
            }
        }
    }

    fun removeDeadPlayer(player: Player) {
        val deadPlayer = deadPlayerMap[player.uniqueId] ?: return
        plugin.server.onlinePlayers.forEach {
            val connection = (it as CraftPlayer).handle.playerConnection
            connection.sendPacket(PacketPlayOutEntityDestroy(deadPlayer.id))
        }
    }

    private fun getSafetyEntityID(): Int {
        val entity = Bukkit.getServer().worlds[0].spawnEntity(Location(Bukkit.getServer().worlds[0], 0.0, 0.0, 0.0), EntityType.ARMOR_STAND)
        val id = entity.entityId
        entity.remove()
        return id
    }
}