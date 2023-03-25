package com.example.m08_mapsapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.m08_mapsapp.R
import com.example.m08_mapsapp.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

//https://coolors.co/palette/5465ff-788bff-9bb1ff-bfd7ff-e2fdff
class MainActivity: AppCompatActivity() {
    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val drawerLayout = binding.drawerLayout
        binding.navigationView.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.fragment_map, R.id.addLocationFragment, R.id.locationsListFragment), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)

        val navigationView = findViewById(com.example.m08_mapsapp.R.id.navigationView) as NavigationView
        val logoutTextView = navigationView.findViewById<TextView>(R.id.logout)

        logoutTextView.setOnClickListener {
            drawerLayout.close()
            navController.navigate(R.id.loginFragment)
        }

    }
    override fun onBackPressed() {

    }

    override fun onSupportNavigateUp(): Boolean {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}