package com.example.itcp

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.itcp.api_files.InterfaceAPI
import com.example.itcp.api_files.RetrofitClientInstance
import com.example.itcp.data_classes.User
import com.example.itcp.fragments.AnnouncementsFragment
import com.example.itcp.fragments.CoursesFragment
import com.example.itcp.fragments.DashboardFragment
import com.example.itcp.fragments.GradesFragment
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView : NavigationView
    private lateinit var headerUsername : TextView
    private lateinit var headerLrn : TextView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {

            it.isChecked = true

            when(it.itemId){
                R.id.nav_dashboard -> replaceFragment(DashboardFragment(), it.title.toString())
                R.id.nav_courses -> replaceFragment(CoursesFragment(), it.title.toString())
                R.id.nav_grades-> replaceFragment(GradesFragment(), it.title.toString())
                R.id.nav_announcements-> replaceFragment(AnnouncementsFragment(), it.title.toString())
                R.id.nav_my_account -> goToActivity(MyAccount())
                R.id.nav_logout-> promptLogout(this)
            }
            true
        }

        fetchUser()
        replaceFragment(DashboardFragment(), "Dashboard")
    }

    private fun promptLogout(context: Context){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Logout?")
        builder.setMessage("Are you sure you want to Logout?")
        builder.setPositiveButton("OK") { dialog, which ->
            User.destroyCredentials(context)
            goToActivity(Login())
        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun replaceFragment(fragment: Fragment, title : String){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchUser(){
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

    fun init(user : User){
        headerUsername = navView.getHeaderView(0).findViewById(R.id.userName)
        headerLrn = navView.getHeaderView(0).findViewById(R.id.header_lrn)

        headerUsername.text = user.name
        headerLrn.text = user.lrn
    }
    private fun goToActivity(activity: Activity){
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }
}