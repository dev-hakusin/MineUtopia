package net.mineutopia.rpg.database.dao

import net.mineutopia.rpg.database.table.UserTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import java.util.*

class User(id: EntityID<UUID>) : Entity<UUID>(id) {
    companion object : EntityClass<UUID, User>(UserTable)

    var name by UserTable.name

    var jobid by UserTable.jobid

    var level by UserTable.level

    var skillPoint by UserTable.skillPoint

    var exp by UserTable.exp

    var money by UserTable.money

}