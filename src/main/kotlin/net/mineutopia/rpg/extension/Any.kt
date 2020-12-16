package net.mineutopia.rpg.extension

fun Any.getFieldValue(fieldName: String): Any? {
    try {
        val field = javaClass.getDeclaredField(fieldName)
        field.isAccessible = true
        return field.get(this)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

fun Any.setFieldValue(fieldName: String, value: Any) {
    try {
        val field = javaClass.getDeclaredField(fieldName)
        field.isAccessible = true
        field.set(this, value)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}