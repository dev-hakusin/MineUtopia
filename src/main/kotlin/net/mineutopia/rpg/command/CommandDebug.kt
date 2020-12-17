package net.mineutopia.rpg.command

import com.mojang.authlib.GameProfile
import net.mineutopia.rpg.entity.nms.v1_16_R2.npc.CustomNPC
import net.mineutopia.rpg.util.NpcUtil
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

object CommandDebug: CommandExecutor {
    override fun onCommand(sender: CommandSender, p1: Command, p2: String, p3: Array<out String>): Boolean {
        if (sender !is Player) {
            return false
        }
        //CustomPig(p0.location)
        CustomNPC(sender.location, GameProfile(UUID.randomUUID(), "test"))
        NpcUtil.spawnDeadPlayer(sender)
        return true
    }
}