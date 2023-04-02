package com.example.itcp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.itcp.api_files.InterfaceAPI
import com.example.itcp.api_files.RetrofitClientInstance
import com.example.itcp.data_classes.LoginResponse
import com.example.itcp.data_classes.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {
    private lateinit var userText: TextView
    private lateinit var passText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }


        val loginBtn = findViewById<Button>(R.id.loginBtn)
        userText = findViewById<TextView>(R.id.username)
        passText = findViewById<TextView>(R.id.passText)
        val progressDialog = ProgressDialog(this)
        loginBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            if (!checkForNulls()) return@setOnClickListener
            progressDialog.setMessage("Loggin In...")
            progressDialog.setCancelable(false)
            progressDialog.show()
            val process = loginProcess(
                userText.text.toString(),
                passText.text.toString(),
                progressDialog
            )
            Log.d("J COLE", "PROCESS: " + process.toString())
        }
    }

    private fun checkForNulls(): Boolean {
        var isValid: Boolean = false
        isValid = if (userText.text.isEmpty()) {
            Toast.makeText(this, "Username is missing.", Toast.LENGTH_SHORT)
            false
        } else true
        isValid = if (passText.text.isEmpty()) {
            Toast.makeText(this, "Password is missing.", Toast.LENGTH_SHORT)
            false
        } else true
        return isValid
    }

    private fun loginProcess(username: String, password: String, progressDialog : ProgressDialog) {
        val retrofit = RetrofitClientInstance(username, password).getRetrofitInstance()
        val api = retrofit.create(InterfaceAPI::class.java)
        val _isLoggedIn = MutableLiveData<Boolean>()
        val isLoggedIn: LiveData<Boolean> = _isLoggedIn

        api.login(username, password).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val status = response.body()?.status
                    val token = response.body()?.accessToken
                    Log.d("J COLE", "STATUS " + status.toString())
                    if (token != null) {
                        User.saveLoginCredentials(baseContext, token)
                    }
                    goToMainActivity(progressDialog)

                } else {
                    val errorBody = response.body()?.msg
                    Log.d("J COLE", "ERROR: $errorBody")
                }
                Log.d("J COLE", response.code().toString() + " " + response.body()?.msg.toString())
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // Handle failure
                Log.e("J COLE", t.localizedMessage)
            }
        })
    }

    fun goToMainActivity(progressDialog : ProgressDialog){
        Handler(Looper.getMainLooper()).postDelayed({
            progressDialog.dismiss()
            startActivity(intent)
            finish()
        }, 1000)
    }
}

