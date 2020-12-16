package net.mineutopia.rpg.util

import net.mineutopia.rpg.extension.partyId
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

object PartyUtil {
    private val partyDataMap: MutableMap<UUID, MutableList<UUID>> = mutableMapOf()

    //mutableList[0]をリーダーとする
    fun getLeader(id: UUID): Player? {
        return Bukkit.getPlayer(partyDataMap[id]?.get(0) ?: return null)
    }

    fun getMember(id: UUID): MutableList<UUID>? {
        return partyDataMap[id]
    }

    private fun getSafetyUUID(): UUID {
        val id = UUID.randomUUID()
        if (partyDataMap.containsKey(id)) {
            return getSafetyUUID()
        }
        return id
    }

    fun createParty(vararg players: Player) {
        val id = getSafetyUUID()
        val list = mutableListOf<UUID>()

        players.forEach {
            list.add(it.uniqueId)
            it.partyId = id
        }
        partyDataMap[id] = list
    }
}