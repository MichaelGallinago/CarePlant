package net.micg.plantcare.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import net.micg.plantcare.R
import net.micg.plantcare.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setUpActivity()
    }

    private fun setUpActivity() = with(ActivityMainBinding.inflate(layoutInflater)) {
        setContentView(root)

        var fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        with((fragment as NavHostFragment).navController) {
            bottomNavView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.alarmsFragment -> {
                        navigate(R.id.alarmsFragment)
                        true
                    }
                    R.id.articlesFragment -> {
                        navigate(R.id.articlesFragment)
                        true
                    }
                    else -> false
                }
            }
        }
    }
}
