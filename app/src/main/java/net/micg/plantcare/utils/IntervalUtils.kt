package net.micg.plantcare.utils

import android.text.Editable
import android.text.TextWatcher
import net.micg.plantcare.presentation.alarmCreation.AlarmCreationViewModel

object IntervalUtils {
    const val MIN = 1
    const val MAX = 365

    fun newWatcher(
        viewModel: AlarmCreationViewModel,
        onValid: (Long) -> Unit
    ): TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, st: Int, c: Int, a: Int) {}
        override fun onTextChanged(s: CharSequence?, st: Int, b: Int, c: Int) {}
        override fun afterTextChanged(s: Editable?) {
            if (viewModel.isUpdating) return
            val v = s.toString().toIntOrNull()
            if (v != null && v in MIN..MAX) onValid(v.toLong())
        }
    }
}
