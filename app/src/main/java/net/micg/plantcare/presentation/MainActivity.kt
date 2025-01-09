package net.micg.plantcare.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import net.micg.plantcare.receiver.AlarmReceiver
import net.micg.plantcare.R
import net.micg.plantcare.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setUpActivity()
        handleIntent()
    }

    private fun setUpActivity() = with(ActivityMainBinding.inflate(layoutInflater)) {
        setContentView(root)

        val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        with((fragment as NavHostFragment).navController) {
            bottomNavView.setOnItemSelectedListener { item ->
                navigateWithSetPopUpTo(
                    this,
                    when (item.itemId) {
                        R.id.alarmsFragment -> R.id.alarmsFragment
                        R.id.articlesFragment -> R.id.articlesFragment
                        else -> R.id.alarmsFragment
                    }
                )
                true
            }
        }
    }

    private fun navigateWithSetPopUpTo(navController: NavController, fragmentId: Int) =
        navController.navigate(
            fragmentId, null, NavOptions.Builder().setPopUpTo(fragmentId, true).build()
        )

    private fun handleIntent() {
        val fragmentTag = intent.getStringExtra(AlarmReceiver.FRAGMENT_TAG) ?: return

        val fragmentId = when (fragmentTag) {
            AlarmReceiver.ALARMS_FRAGMENT_TAG -> R.id.alarmsFragment
            else -> R.id.articlesFragment
        }

        val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        (fragment as NavHostFragment).navController.navigate(fragmentId)
    }
}
