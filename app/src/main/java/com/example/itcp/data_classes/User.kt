package com.example.itcp.data_classes

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

data class User(
    val id : Int, val name : String, val lrn : String,
    val grade : Int, val section : String, val strand : String,
    val strandId : Int, val gender : String, val enrolledDate : String,
    val address : String, val phone : String, val dob : String,
    val username: String, val password: String
    ){

    companion object {
        fun saveLoginCredentials(context: Context, token : String) {
            val sharedPref = context.getSharedPreferences("login", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("token", token)
                apply()
            }
        }
        fun getLoginCredentials(context: Context): String? {
            val sharedPref = context.getSharedPreferences("login", Context.MODE_PRIVATE)
            return sharedPref.getString("token", null)
        }

        fun destroyCredentials(context: Context){
            val sharedPrefs = context.getSharedPreferences("login", Context.MODE_PRIVATE)
            val editor = sharedPrefs.edit()
            editor.remove("token")
            editor.apply()
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getUsernamePassword(token: String): Pair<String,String> {
            val decodedBytes = Base64.getDecoder().decode(token)
            val decodedString = String(decodedBytes)
            val username = decodedString.substring(0, decodedString.indexOf(":"))
            val password = decodedString.substring(decodedString.indexOf(":") + 1)
            return Pair(username,password)
        }
    }
}

