package com.omar.globotour

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar           : MaterialToolbar
    private lateinit var navController     : NavController
    private lateinit var navigationView    : NavigationView
    private lateinit var drawerLayout      : DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Initialize the views
        toolbar        = findViewById(R.id.activity_main_toolbar)
        navigationView = findViewById(R.id.nav_view)
        drawerLayout   = findViewById(R.id.drawer_layout)

        // Get NavHostFragment and NavController
        val navHosFrage = supportFragmentManager.findFragmentById(R.id.nav_host_frag) as NavHostFragment
        navController   = navHosFrage.navController

        // Define AppBarConfiguration: Connect DrawerLayout with Navigation Component
        val appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        // Connect Toolbar with NavController
        toolbar.setupWithNavController(navController, appBarConfiguration)

        // Connect NavigationView with NavController
        navigationView.setupWithNavController(navController)

    }

    override fun onBackPressed() {
        if(drawerLayout.isOpen){
            drawerLayout.close()
    }
        else{
            super.onBackPressed()
        }
    }
        }