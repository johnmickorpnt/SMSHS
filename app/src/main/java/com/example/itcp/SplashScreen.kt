package com.example.itcp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.itcp.data_classes.User

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        try {
            this.supportActionBar!!.hide()
        }
            catch (e: NullPointerException) {
        }

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler(Looper.getMainLooper()).postDelayed({
//            Logic if user is logged in
            isLoggedIn(baseContext)

        }, 3000)
    }

    private fun isLoggedIn(context: Context){
        val userCredentials = User.getLoginCredentials(context)
        if (userCredentials != null) {
            // Do something with the data
            Log.d("J COLE", "SplashScreen " + userCredentials)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            // Data was not saved
            Log.d("J COLE", "No data saved")
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun login(){
        TODO("Separate Login function so that the system can " +
                "validate if the user still exists in the DB")
    }
}