package com.ucb.bottomnavigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        // Set default tab to the second tab (MyListView)
        bottomNavigationView.selectedItemId = R.id.nav_list

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            val selectedFragment: Fragment = when (item.itemId) {
                R.id.nav_calculator -> CalculatorFragment()
                R.id.nav_list -> TaskListFragment()
                R.id.nav_profile -> ProfileFragment()
                else -> TaskListFragment()
            }
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit()
            true
        }

        // Load the default fragment (task list)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, TaskListFragment()).commit()
    }
}
