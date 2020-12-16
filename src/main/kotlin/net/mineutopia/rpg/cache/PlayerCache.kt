package net.mineutopia.rpg.cache

import net.mineutopia.rpg.database.dao.Skill
import net.mineutopia.rpg.database.dao.User
import net.mineutopia.rpg.player.PlayerJob
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class PlayerCache(private val uniqueId: UUID, private val playerName: String) : Cache(){
    override fun read() {
        transaction {
            UserEntityData(uniqueId, playerName).run {
                playerJob = getPlayerJobFromId(user.jobid.toInt())
                level = user.level
                skillPoint = user.skillPoint
                exp = user.exp
                money = user.money

                skill1 = skill.skill1
                skill2 = skill.skill2
                skill3 = skill.skill3
                skill4 = skill.skill4
                skill5 = skill.skill5
            }
        }
    }
    override fun write() {
        transaction {
            UserEntityData(uniqueId, playerName).run {
                user.jobid = playerJob.jobId.toLong()
                user.level = level
                user.skillPoint = skillPoint
                user.exp = exp
                user.money = money

                skill.selected = selected
                skill.skill1 = skill1
                skill.skill2 = skill2
                skill.skill3 = skill3
                skill.skill4 = skill4
                skill.skill5 = skill5
            }
        }
    }
}

private class UserEntityData(uniqueId: UUID, playerName: String) {
    val user = User.findById(uniqueId)?.apply {
        name = playerName
    } ?: User.new(uniqueId) {
        name = playerName
    }
    val skill = Skill.findById(uniqueId)?.apply {
        name = playerName
    } ?: Skill.new(uniqueId) {
        name = playerName
    }
}

private val jobIdMap: MutableMap<Int, PlayerJob> = mutableMapOf()

private fun getPlayerJobFromId(id: Int): PlayerJob {
    if (jobIdMap.isEmpty()) {
        PlayerJob.values().forEach {
            jobIdMap[it.jobId] = it
        }
    }

    return jobIdMap[id] ?: PlayerJob.TRAVELLER
}