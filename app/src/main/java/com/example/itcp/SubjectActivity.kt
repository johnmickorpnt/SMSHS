package com.example.itcp

import android.app.ProgressDialog
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.itcp.adapters.ModulesAdapter
import com.example.itcp.api_files.InterfaceAPI
import com.example.itcp.api_files.RetrofitClientInstance
import com.example.itcp.data_classes.User
import com.example.itcp.models.CoursesModel
import com.example.itcp.models.ModuleModel
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class SubjectActivity : AppCompatActivity() {
    private lateinit var user : User
    private lateinit var subject : CoursesModel
    private lateinit var courseTitle : TextView
    private lateinit var codeLabel : TextView
    private lateinit var deptLabel : TextView
    private lateinit var desc : TextView

    private lateinit var modulesAdapter: ModulesAdapter

    private lateinit var modulesRecyclerView: RecyclerView
    private lateinit var progressDialog : ProgressDialog
    private lateinit var courseHeader : RelativeLayout
    private lateinit var courseContainer : RelativeLayout


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val actionBar: ActionBar? = supportActionBar

        // set the background color
        actionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.link_color)))
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        val extras = intent.extras
        val value = extras?.getString("ITEM_ID")
        if (value != null) {
            dataInit(value)
        }

        setContentView(R.layout.activity_subject)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun dataInit(id : String){
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
                if(response.isSuccessful) {
                    response.body()?.let { fetchCourse(it, api, currToken, id) }
                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("J COLE", t.localizedMessage)
            }
        })
    }

    private fun fetchCourse(currUser: User, api : InterfaceAPI, token: String, id : String){
        user = currUser
        val courseCall = api.getSubject(token, id)
        courseCall.enqueue(object : Callback<CoursesModel> {
            override fun onResponse(call: Call<CoursesModel>, response: Response<CoursesModel>) {
                if(response.isSuccessful) fetchModules(user, api, token, id, response.body()!!)
            }
            override fun onFailure(call: Call<CoursesModel>, t: Throwable) {
                Log.e("J COLE", "FETCH:"  + t.localizedMessage)
            }
        })
    }

    private fun fetchModules(currUser: User, api : InterfaceAPI, token: String, id : String, course: CoursesModel){
        val modulesCall = api.getModules(token, id)
        modulesCall.enqueue(object : Callback<ArrayList<ModuleModel>>{
            override fun onResponse(
                call: Call<ArrayList<ModuleModel>>,
                response: Response<ArrayList<ModuleModel>>
            ) {
                if(response.isSuccessful) {
                    makeCourse(course)
                    makeModules(response.body()!!, course)
                }
            }
            override fun onFailure(call: Call<ArrayList<ModuleModel>>, t: Throwable) {
                Log.e("J COLE", t.localizedMessage)
            }
        })
    }

    private fun makeModules(modules : ArrayList<ModuleModel>, course: CoursesModel){
        var singleLayoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        modulesRecyclerView = findViewById(R.id.downloads_recyclerview)
        modulesAdapter = ModulesAdapter(modules, user, course.subj_code)
        modulesRecyclerView.adapter = modulesAdapter

        modulesRecyclerView.isNestedScrollingEnabled = false
        modulesRecyclerView.layoutManager = singleLayoutManager
        courseHeader = findViewById(R.id.courseIntroLayout)
        courseContainer = findViewById(R.id.courseIntroContainer)

        val random = Random
        val colors = arrayOf("#7D5A50", "#FF2E63", "#B83B5E", "#3F72AF", "#967E76")
        val randomNumberInRange = random.nextInt(0, 4) // generates a random number between 5 and 14
        courseContainer.setBackgroundColor(Color.parseColor(colors[randomNumberInRange]))




    }

    private fun makeCourse(course: CoursesModel){
        subject = course
        courseTitle = findViewById<TextView>(R.id.mainCourseTitle)
        codeLabel = findViewById<TextView>(R.id.code_label)
        deptLabel = findViewById<TextView>(R.id.dept_label)
        desc = findViewById<TextView>(R.id.description)

        courseTitle.text = subject.subj_name
        codeLabel.text = subject.subj_code
        deptLabel.text = if(subject.dept != "") subject.dept else "N/A"
        desc.text = subject.subj_desc
        val imgFile = course.subj_image.replace(" ", "%20")
        val imageUrl = "https://smshs-capstone.000webhostapp.com/imgsubject/$imgFile"
        Picasso.get()
            .load(imageUrl)
            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
            .networkPolicy(NetworkPolicy.OFFLINE)
            .into(object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                courseHeader.background = BitmapDrawable(resources, bitmap)
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                Log.e("J COLE", e.toString())
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                Log.d("J COLE", imageUrl)
            }
        })

        Handler(Looper.getMainLooper()).postDelayed({
            progressDialog.dismiss()
        }, 500)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}