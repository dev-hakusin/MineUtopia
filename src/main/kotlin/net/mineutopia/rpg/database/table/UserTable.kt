package net.mineutopia.rpg.database.table

import org.jetbrains.exposed.dao.IdTable
import java.util.*

object UserTable : IdTable<UUID>("users") {
    //Player UUID
    override val id = uuid("uniqueId").primaryKey().entityId()
    //Player Name
    val name = varchar("name", 20)
    //使用中のPlayerJobのId
    val jobid = long("jobId").default(0L)
    //Player level
    val level = long("level").default(0L)

    val skillPoint = long("skillPoint").default(0L)
    //総獲得経験値
    val exp = long("exp").default(0L)
    //所持金
    val money = long("money").default(0L)
}