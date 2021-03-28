package com.pruebatalento

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.activity_main.*
import androidx.navigation.ui.setupWithNavController
import pruebatalento.BackPressedListener
import pruebatalento.operations.MainViewModel
import pruebatalento.operations.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProviders.of(this, MainViewModelFactory())
            .get(MainViewModel::class.java)

        setContentView(R.layout.activity_main)

        bottomNavigationView.setupWithNavController(
                Navigation.findNavController(
                        this,
                        R.id.main_nav_host_fragment
                )
        )

    }

    override fun onSupportNavigateUp() = findNavController(R.id.main_nav_host_fragment).navigateUp()

    override fun onBackPressed() {
        val fragment: BackPressedListener? = getCurrentBackPressedListenerFragment()
        if (fragment == null) {
            super.onBackPressed()
        } else {
            fragment.onBackPressed()
        }
    }

    private fun getCurrentBackPressedListenerFragment(): BackPressedListener? {
        val fragment: Fragment? = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment)
        if (fragment != null && fragment is NavHostFragment) {
            for (childFragment in fragment.childFragmentManager.fragments) {
                if (childFragment is BackPressedListener) {
                    return childFragment
                }
            }
        }

        return null
    }


    fun showMenu() {
        bottomNavigationView.visibility = View.VISIBLE
    }

    fun hideMenu() {
        bottomNavigationView.visibility = View.GONE
    }

}