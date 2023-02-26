package com.example.itcp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView : NavigationView
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
                R.id.nav_attendance -> replaceFragment(AttendanceFragment(), it.title.toString())
                R.id.nav_help -> Toast.makeText(applicationContext, "Help", Toast.LENGTH_SHORT).show()
                R.id.nav_logout-> promptLogout()
            }
            true
        }

        replaceFragment(DashboardFragment(), "Dashboard")
    }

    private fun promptLogout(){
        Toast.makeText(applicationContext, "Logout", Toast.LENGTH_SHORT).show()
    }

    private fun replaceFragment(fragment: Fragment, title : String){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }
}