package net.mineutopia.rpg.util

import net.mineutopia.rpg.MineUtopia
import net.mineutopia.rpg.config.DatabaseConfig
import net.mineutopia.rpg.database.table.SkillTable
import net.mineutopia.rpg.database.table.UserTable
import org.bukkit.ChatColor
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.SQLException

object DatabaseUtil {
    private val plugin = MineUtopia.PLUGIN

    @Throws(SQLException::class, ClassNotFoundException::class)
    fun prepareDatabase() {
        try {
            Database.connect("jdbc:mysql://${DatabaseConfig.HOST}/${DatabaseConfig.DATABASE}",
                    "com.mysql.jdbc.Driver", DatabaseConfig.USER, DatabaseConfig.PASSWORD)

            transaction {
                SchemaUtils.createMissingTablesAndColumns(
                        UserTable,
                        SkillTable
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            val server = plugin.server
            server.consoleSender.sendMessage("${ChatColor.RED}[MineUtopia] MySQLに接続できませんでした")
            server.consoleSender.sendMessage("${ChatColor.RED}[MineUtopia] サーバーを停止します")
            server.shutdown()
            return
        }
        plugin.server.consoleSender.sendMessage("${ChatColor.GREEN}[MineUtopia] MySQLに正常に接続しました")
    }
}