package net.mineutopia.rpg.cache

import java.util.*

object PlayerCacheMemory {
    private val playerCacheMap = mutableMapOf<UUID, PlayerCache>()

    fun get(uniqueId: UUID) = playerCacheMap[uniqueId]!!

    fun set(uniqueId: UUID, cache: PlayerCache) {
        playerCacheMap[uniqueId] = cache
    }

    fun contains(uniqueId: UUID) = playerCacheMap.contains(uniqueId)

    fun add(uniqueId: UUID, playerName: String) {

        val newCache = PlayerCache(uniqueId, playerName)

        newCache.read()

        playerCacheMap[uniqueId] = newCache
    }

    fun save(uniqueId: UUID, isAsync: Boolean) {
        save(playerCacheMap[uniqueId]
                ?: return, isAsync)
    }

    fun saveThenRemoved(uniqueId: UUID, isAsync: Boolean) {
        save(playerCacheMap.remove(uniqueId)
                ?: return, isAsync)
    }

    private fun save(playerCache: PlayerCache, isAsync: Boolean) {
        playerCache.run {
            if (isAsync) writeAsync() else write()
        }
    }

}