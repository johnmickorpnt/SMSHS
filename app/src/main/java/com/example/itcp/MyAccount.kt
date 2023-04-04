package com.example.itcp

import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import com.example.itcp.api_files.InterfaceAPI
import com.example.itcp.api_files.RetrofitClientInstance
import com.example.itcp.data_classes.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyAccount : AppCompatActivity() {
    private lateinit var nameText : TextView
    private lateinit var lrnText : TextView
    private lateinit var strandText : TextView
    private lateinit var gradeText : TextView
    private lateinit var dobText : TextView
    private lateinit var addressText : TextView
    private lateinit var contactText : TextView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_account)
        nameText = findViewById(R.id.nameText)
        lrnText = findViewById(R.id.lrnTextView)
        strandText = findViewById(R.id.strandText)
        gradeText = findViewById(R.id.gradeTextLabel)
        dobText = findViewById(R.id.date_of_birth_text)
        addressText = findViewById(R.id.addressText)
        contactText = findViewById(R.id.contactText)
        val actionBar: ActionBar? = supportActionBar

        // set the background color
        actionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.link_color)))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        fetchUser()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchUser(){
        val token : String? = User.getLoginCredentials(this)
        val credentials = token?.let { User.getUsernamePassword(it) }
        val u : String = credentials?.first ?: ""
        val p : String = credentials?.second ?: ""

        if(u == "" && p == "") return

        val retrofit = RetrofitClientInstance(u,p).getRetrofitInstance()
        val api = retrofit.create(InterfaceAPI::class.java)
        val currToken = User.getLoginCredentials(this)
        val userCall = currToken?.let { api.getUser(it) }

        userCall?.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful) init(response.body()!!)
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("J COLE", t.localizedMessage)
            }
        })
    }

    fun init(user:User){
        nameText.text = user.name
        lrnText.text = if (user.lrn != null) user.lrn else "12378125679"
        strandText.text = user.strand
        gradeText.text = user.grade.toString()
        dobText.text = user.dob
        addressText.text = user.address
        contactText.text = "84565648"

        nameText.keyListener = null
        lrnText.keyListener = null
        strandText.keyListener = null
        gradeText.keyListener = null
        dobText.keyListener = null
        addressText.keyListener = null
        contactText.keyListener = null

        Log.d("J COLE", user.toString())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}