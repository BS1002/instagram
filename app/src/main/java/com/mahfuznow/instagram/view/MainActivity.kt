package com.mahfuznow.instagram.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.mahfuznow.instagram.R
import com.mahfuznow.instagram.databinding.ActivityMainBinding

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
        NavigationUI.setupWithNavController(bottomNavigation, navController);
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }
}