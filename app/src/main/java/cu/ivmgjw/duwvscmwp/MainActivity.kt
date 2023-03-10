package cu.ivmgjw.duwvscmwp

import android.app.PendingIntent.getActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import cu.ivmgjw.duwvscmwp.databinding.ActivityMainBinding
import cu.ivmgjw.duwvscmwp.databinding.FragmentStartBinding
import cu.ivmgjw.duwvscmwp.ui.start.StartFragment
import cu.ivmgjw.duwvscmwp.webview.NetworkChecker

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}