package net.mineutopia.rpg.command

import net.mineutopia.rpg.mob.nms.v1_16_R2.CustomPig
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object CommandDebug: CommandExecutor {
    override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>): Boolean {
        if (p0 !is Player) {
            return false
        }
        CustomPig(p0.location)
        return true
    }
}