package net.mineutopia.rpg.database.table

import org.jetbrains.exposed.dao.IdTable
import java.util.*

object SkillTable: IdTable<UUID>("skills") {
    override val id = uuid("uniqueId").primaryKey().entityId()

    val name = varchar("name", 20)

    val selected = integer("selected").default(1)

    val skill1 = varchar("skill1", 20).default("unknown")
    val skill2 = varchar("skill2", 20).default("unknown")
    val skill3 = varchar("skill3", 20).default("unknown")
    val skill4 = varchar("skill4", 20).default("unknown")
    val skill5 = varchar("skill5", 20).default("unknown")
}