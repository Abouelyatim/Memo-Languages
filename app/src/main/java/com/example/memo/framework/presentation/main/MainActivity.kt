package com.example.memo.framework.presentation.main

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.memo.R
import com.example.memo.databinding.ActivityMainBinding
import com.example.memo.framework.presentation.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var navController: NavController

    private lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var binding: ActivityMainBinding

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_fragments_container) as NavHostFragment
        navController = navHostFragment.navController

        setupBottomNavigationView()

        subscribeObservers()

        if( intent.action.equals( "android.intent.action.SEND" ) ){
            val url = intent.extras?.getString( "android.intent.extra.TEXT" )
            val bundle = bundleOf("SEARCH_URL" to url)
            navController.navigate(R.id.nav_save,bundle)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        };
    }

    private fun setupBottomNavigationView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.setupWithNavController(navController)
    }

    fun subscribeObservers() {

    }

    override fun displayProgressBar(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}









