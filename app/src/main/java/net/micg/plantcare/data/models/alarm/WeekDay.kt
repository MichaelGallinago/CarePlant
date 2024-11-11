package net.micg.plantcare.data.models.alarm

enum class WeekDay(val flag: Int) {
    MONDAY(1 shl 0),   // 0000001
    TUESDAY(1 shl 1),  // 0000010
    WEDNESDAY(1 shl 2),// 0000100
    THURSDAY(1 shl 3), // 0001000
    FRIDAY(1 shl 4),   // 0010000
    SATURDAY(1 shl 5), // 0100000
    SUNDAY(1 shl 6)    // 1000000
}
