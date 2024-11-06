package net.micg.plantcare.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import net.micg.plantcare.R
import net.micg.plantcare.databinding.ActivityMainBinding
import net.micg.plantcare.ui.fragments.AlarmsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        //setUpBottomNavigationClickListener()

        setContentView(R.layout.activity_main)

        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

        //val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        //val navController = findNavController(R.id.alarmsFragment)
        //bottomNavigationView.setupWithNavController(navController);
    }
    /*
    private fun setUpBottomNavigationClickListener() {
        binding.bottomNavView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_articles -> scheduleAdapter.submitValue(getScheduleForThisDay())
                else -> scheduleAdapter.submitValue(getScheduleForThisWeek())
            }
            true
        }
    }*/
}
