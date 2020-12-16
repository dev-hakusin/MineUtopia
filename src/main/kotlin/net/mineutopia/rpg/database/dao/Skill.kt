package net.mineutopia.rpg.database.dao

import net.mineutopia.rpg.database.table.SkillTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import java.util.*

class Skill(id: EntityID<UUID>) : Entity<UUID>(id) {
    companion object : EntityClass<UUID, Skill>(SkillTable)

    var name by SkillTable.name

    var selected by SkillTable.selected

    var skill1 by SkillTable.skill1
    var skill2 by SkillTable.skill2
    var skill3 by SkillTable.skill3
    var skill4 by SkillTable.skill4
    var skill5 by SkillTable.skill5
}