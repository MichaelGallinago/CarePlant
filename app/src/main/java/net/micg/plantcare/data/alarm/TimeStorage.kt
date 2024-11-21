package net.micg.plantcare.data.alarm

data class TimeStorage(
    var year: Int,
    var month: Int,
    var dayOfMonth: Int,
    var hourOfDay: Int,
    var minute: Int,
) {
    val dateFormated get() = "%02d/%02d/%04d".format(dayOfMonth, month + 1, year)
    val timeFormated get() = "%02d:%02d".format(hourOfDay, minute)
}
