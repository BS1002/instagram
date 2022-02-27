package com.mahfuznow.instagram.ui.main.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.mahfuznow.instagram.R
import com.mahfuznow.instagram.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_Instagram) //Changing the app theme
        setContentView(binding.root)

        val toolbar = binding.toolbar
        val bottomNavigation = binding.bottomNavigation

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        //Setting custom toolbar
        setSupportActionBar(toolbar)

        //Setup Action Bar
        NavigationUI.setupActionBarWithNavController(this, navController)
        //Setup Bottom Navigation
        NavigationUI.setupWithNavController(bottomNavigation, navController)

        //Customising ActionBar and BottomNavigation based on the destination
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.viewStoryFragment -> {
                    supportActionBar?.hide()
                    bottomNavigation.visibility = View.GONE
                }
                else -> {
                    supportActionBar?.show()
                    bottomNavigation.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }
}