package net.micg.plantcare.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import net.micg.plantcare.R
import net.micg.plantcare.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        with(ActivityMainBinding.inflate(layoutInflater)) {
            setContentView(root)

            var fragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view)
            with((fragment as NavHostFragment).navController) {
                bottomNavView.setupWithNavController(this)

                addOnDestinationChangedListener { _, destination, _ ->
                    when (destination.id) {
                        R.id.alarmsFragment or R.id.alarmCreationFragment -> R.id.alarmsFragment
                        R.id.articlesFragment or R.id.articleFragment -> R.id.articlesFragment
                        else -> null
                    }?.let { bottomNavView.menu.findItem(it)?.isChecked = true }
                }
            }
        }
    }
}
