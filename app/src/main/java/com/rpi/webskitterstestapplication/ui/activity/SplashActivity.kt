package com.rpi.webskitterstestapplication.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.property.app.sharedpref.SharedPrefDetails
import com.rpi.webskitterstestapplication.R

class SplashActivity : AppCompatActivity() {
    private lateinit var sharedPref: SharedPrefDetails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        initVariable()
        moveToNextScreen(2000)
    }

    private fun initVariable() {
        sharedPref = SharedPrefDetails(this)
    }

    private fun moveToLoginScreen() {
        val loginIntent = Intent(this@SplashActivity, LoginActivity::class.java)
        startActivity(loginIntent)
        finish()
    }

    private fun moveToMainScreen() {
        val mainIntent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(mainIntent)
        finish()
    }

    private fun moveToNextScreen(delay: Long) {
        val handler = Handler().postDelayed({
            sharedPref.userToken?.let {token ->
                if (token.isNotEmpty()) {
                   moveToMainScreen()
                } else {
                   moveToLoginScreen()
                }
            } ?: moveToLoginScreen()
        }, delay)
    }
}