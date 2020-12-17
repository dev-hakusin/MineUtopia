package net.mineutopia.rpg.entity.nms.v1_16_R2.npc

import com.mojang.authlib.GameProfile
import net.minecraft.server.v1_16_R2.*
import net.mineutopia.rpg.util.NpcUtil
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_16_R2.CraftServer
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer

class CustomNPC(loc: Location, gameprofile: GameProfile) : EntityPlayer((Bukkit.getServer() as CraftServer).server as MinecraftServer, (loc.world as CraftWorld).handle, gameprofile, PlayerInteractManager((loc.world as CraftWorld).handle)) {
    init {
        playerConnection = PlayerConnection((Bukkit.getServer() as CraftServer).server, NpcUtil.getManager(), this)

        this.setPosition(loc.x, loc.y, loc.z)
        val world = (loc.world as CraftWorld).handle
        Bukkit.getOnlinePlayers().forEach {
            val player = (it as CraftPlayer).handle
            val connection = player.playerConnection
            connection.sendPacket(PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, this))
        }
        world.addEntity(this)
    }
}