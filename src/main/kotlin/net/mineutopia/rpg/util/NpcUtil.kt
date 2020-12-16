package net.mineutopia.rpg.util

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.PropertyMap
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.minecraft.server.v1_16_R2.*
import net.mineutopia.rpg.MineUtopia
import net.mineutopia.rpg.extension.setFieldValue
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_16_R2.CraftServer
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import java.util.*

object NpcUtil {
    private val plugin = MineUtopia.PLUGIN

    private val deadPlayerMap: MutableMap<UUID, DeadPlayer> = mutableMapOf()
    private data class DeadPlayer(val uniqueId: UUID, val id: Int)

    fun spawnDeadPlayer(deadPlayer: Player) {
        val location = deadPlayer.location

        val server = (Bukkit.getServer() as CraftServer).server as MinecraftServer
        val world = (Bukkit.getServer().worlds[0] as CraftWorld).handle

        val profile: GameProfile = (deadPlayer as CraftPlayer).profile

        val uuid = UUID.randomUUID()
        val dummyProfile = GameProfile(uuid, deadPlayer.name)

        val texture = (profile.properties.get("textures")).toMutableList()[0]
        val pm: PropertyMap = dummyProfile.properties
        pm.put("textures", texture)

        val id = getSafetyEntityID()
        deadPlayerMap[deadPlayer.uniqueId] = DeadPlayer(uuid, id)

        //NPCを作成
        val fakePlayer = EntityPlayer(server, world, dummyProfile, PlayerInteractManager(world))
        fakePlayer.setPosition(location.x, location.y, location.z)

        val watcher = fakePlayer.dataWatcher
        val b = 0x40.toByte()

        watcher.set(DataWatcherObject(16, DataWatcherRegistry.a), b)
        fakePlayer.pose = EntityPose.SLEEPING

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