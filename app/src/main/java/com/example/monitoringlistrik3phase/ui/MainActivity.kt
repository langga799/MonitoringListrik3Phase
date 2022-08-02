package com.example.monitoringlistrik3phase.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.monitoringlistrik3phase.R
import com.example.monitoringlistrik3phase.databinding.ActivityMainBinding
import com.example.monitoringlistrik3phase.helper.inVisible
import com.example.monitoringlistrik3phase.helper.visible
import com.example.monitoringlistrik3phase.service.ServiceNotification
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_dashboard)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home -> {
                    navView.visible()
                }
                R.id.navigation_history -> {
                    navView.visible()
                }
                R.id.navigation_setting -> {
                    navView.visible()
                }
                else -> {
                    navView.inVisible()
                }
            }
        }


        startService(Intent(this, ServiceNotification::class.java))

    }


}