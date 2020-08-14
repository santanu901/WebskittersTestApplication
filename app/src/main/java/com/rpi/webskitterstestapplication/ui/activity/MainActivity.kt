package com.rpi.webskitterstestapplication.ui.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.property.app.sharedpref.SharedPrefDetails
import com.rpi.webskitterstestapplication.R
import com.rpi.webskitterstestapplication.ui.fragment.HomeMapFragment
import com.rpi.webskitterstestapplication.ui.fragment.ProfileFragment
import com.rpi.webskitterstestapplication.ui.fragment.UserListFragment
import com.rpi.webskitterstestapplication.utils.NetworkUtility
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_common.*
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPref: SharedPrefDetails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initVariable()
        initListeners()
        initToolbar()
        if (savedInstanceState == null && intent.extras == null) {
            selectNavItem(R.id.navigation_map)
        }
    }

    private fun initVariable() {
        sharedPref = SharedPrefDetails(this)
    }

    private fun initListeners() {
        bottom_navView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            selectNavItem(item.itemId)
            true
        })
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.clear()
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                logoutHandler()
            }
        }
        return true
    }

    private fun logoutHandler() {
        if (this::sharedPref.isInitialized) {
            sharedPref.setClear()
            moveToLoginScreen()
        }
    }

    private fun moveToLoginScreen() {
        val loginIntent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(loginIntent)
        finish()
    }

    private fun selectNavItem(selectedId: Int) {
        var fragment: Fragment? = null
        val fts =
            supportFragmentManager.beginTransaction()
        fts.setCustomAnimations(
            R.anim.fragment_slide_in_left,
            0,
            0,
            R.anim.fragment_slide_out_left
        )
        when (selectedId) {
            R.id.navigation_map -> {
                fragment = HomeMapFragment.newInstance()
                fts.replace(R.id.flMainContainer, fragment).commit()
            }
            R.id.navigation_user_list -> {
                fragment = UserListFragment.newInstance()
                fts.replace(R.id.flMainContainer, fragment).commit()
            }
            R.id.navigation_profile -> {
                fragment = ProfileFragment.newInstance()
                fts.replace(R.id.flMainContainer, fragment).commit()
            }
        }
    }

    fun setActionBarTitle(title: String?) {
        toolbar_title.text = title
    }
}