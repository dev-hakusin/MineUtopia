package net.mineutopia.rpg.cache

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.mineutopia.rpg.player.PlayerJob

abstract class Cache {

    abstract fun read()

    abstract fun write()

    fun readAsync() {
        GlobalScope.launch { read() }
    }

    fun writeAsync() {
        GlobalScope.launch { write() }
    }

    //UserTable
    var playerJob: PlayerJob = PlayerJob.TRAVELLER
    var level: Long = 0L

    var skillPoint: Long = 0L

    var exp: Long = 0L

    var money: Long = 0L

    //SkillTable
    var selected: Int = 1

    var skill1: String = "unset"
    var skill2: String = "unset"
    var skill3: String = "unset"
    var skill4: String = "unset"
    var skill5: String = "unset"
}
