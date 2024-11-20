package net.micg.plantcare.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import net.micg.plantcare.R
import net.micg.plantcare.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container_view) as NavHostFragment

        val navController = navHostFragment.navController
        binding.bottomNavView.setupWithNavController(navController)

        val alarmsDestinations = setOf(R.id.alarmsFragment, R.id.alarmCreationFragment)
        val articlesDestinations = setOf(R.id.articlesFragment, R.id.articleFragment)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                in alarmsDestinations -> R.id.alarmsFragment
                in articlesDestinations -> R.id.articlesFragment
                else -> null
            }?.let { binding.bottomNavView.menu.findItem(it)?.isChecked = true }
        }
    }
}
