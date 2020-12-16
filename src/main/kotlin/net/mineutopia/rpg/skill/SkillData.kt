package net.mineutopia.rpg.skill

object SkillData {
    private val map: MutableMap<String, Skill> = mutableMapOf()

    fun register(skill: Skill) {
        map[skill.id] = skill
    }

    fun get(id: String): Skill? {
        return map[id]
    }
}