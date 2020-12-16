package net.mineutopia.rpg.command

import net.mineutopia.rpg.item.ItemData
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

object CommandGet: TabExecutor {
    override fun onTabComplete(sender: CommandSender, cmd: Command, commandLabel: String, args: Array<out String>): MutableList<String> {
        if (!sender.isOp) {
            return mutableListOf()
        }
        if (sender !is Player) {
            return mutableListOf()
        }
        if (args.isNullOrEmpty()) {
            return mutableListOf()
        }
        val itemList = mutableListOf<String>()
        ItemData.getMap().keys.toMutableList().forEach {
            itemList.add("mineutopia:${it.toLowerCase()}")
        }
        val matchList = mutableListOf<String>()
        itemList.forEach {
            if (it.startsWith(args[0])) {
                matchList.add(it)
            }
        }
        return matchList
    }

    //デバッグ用なので実装が雑。
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            return false
        }
        if (!sender.isOp) {
            return false
        }
        if (args.isNullOrEmpty()) {
            return false
        }
        if (args[0].startsWith("mineutopia:")) {
            return try {
                sender.inventory.addItem(ItemData.get(args[0].removePrefix("mineutopia:"))?.toItemStack())
                true
            } catch (e: Exception) {
                false
            }
        }
        return false
    }
}