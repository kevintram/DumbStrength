package com.kiwicorp.supersimplegymapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.google.android.material.tabs.TabLayout
import com.kiwicorp.supersimplegymapp.NavGraphDirections.Companion.toActivitiesFragment
import com.kiwicorp.supersimplegymapp.NavGraphDirections.Companion.toWorkoutsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupTabLayout()
        setUpNavController()
    }

    private fun setupTabLayout() {
        tabLayout= findViewById(R.id.tab_layout)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val navController = findNavController(R.id.nav_host_fragment)
                when (tab!!.position) {
                    0 -> navController.navigate(toWorkoutsFragment())
                    1 -> navController.navigate(toActivitiesFragment())
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
        })
    }

    private fun setUpNavController() {
        findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener { _, destination, _ ->
            tabLayout.visibility = when(destination.id) {
                R.id.workoutsFragment -> View.VISIBLE
                R.id.activitiesFragment -> View.VISIBLE
                else -> View.GONE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}