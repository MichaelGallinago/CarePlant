package net.micg.plantcare.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import net.micg.plantcare.R

object CalendarPermissionHelper {

    private val CALENDAR_PERMISSIONS = arrayOf(
        Manifest.permission.READ_CALENDAR,
        Manifest.permission.WRITE_CALENDAR
    )

    fun bind(
        fragment: Fragment,
        switch: SwitchCompat,
        onGranted: () -> Unit = {}
    ) {
        val ctx = fragment.requireContext()
        switch.isChecked = CalendarSharedPrefs.isSwitchEnabled(ctx)

        val launcher = fragment.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { map ->
            val granted = CALENDAR_PERMISSIONS.all { map[it] == true }
            if (granted) {
                switch.isChecked = true
                CalendarSharedPrefs.setSwitchEnabled(ctx, true)
                onGranted()
            } else {
                Toast.makeText(ctx, R.string.require_calendar, Toast.LENGTH_SHORT).show()
            }
        }

        switch.setOnCheckedChangeListener { _, _ ->
            if (hasCalendarPermissions(ctx)) {
                val checked = !switch.isChecked
                switch.isChecked = checked
                CalendarSharedPrefs.setSwitchEnabled(ctx, checked)
                if (checked) onGranted()
            } else {
                launcher.launch(CALENDAR_PERMISSIONS)
            }
        }

        fragment.lifecycleScope.launchWhenResumed {
            if (hasCalendarPermissions(ctx) && !switch.isChecked) {
                switch.isChecked = true
                CalendarSharedPrefs.setSwitchEnabled(ctx, true)
            }
        }
    }

    private fun hasCalendarPermissions(ctx: Context) = CALENDAR_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(ctx, it) == PackageManager.PERMISSION_GRANTED
    }
}
