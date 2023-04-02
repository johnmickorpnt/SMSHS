package com.example.itcp.fragments

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.itcp.api_files.InterfaceAPI
import com.example.itcp.R
import com.example.itcp.SubjectActivity
import com.example.itcp.api_files.RetrofitClientInstance
import com.example.itcp.data_classes.User
import com.example.itcp.adapters.AnnouncementsAdapter
import com.example.itcp.adapters.MyAdapter
import com.example.itcp.adapters.OnItemClickListener
import com.example.itcp.models.AnnouncementModel
import com.example.itcp.models.CoursesModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardFragment : Fragment(), OnItemClickListener {
    private lateinit var announcementsAdapter: AnnouncementsAdapter
    private lateinit var announcementList : ArrayList<AnnouncementModel>
    private lateinit var announcementRecyclerView : RecyclerView

    private lateinit var adapter: MyAdapter
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var courseArrayList : ArrayList<CoursesModel>

    private lateinit var user : User


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressDialog = ProgressDialog(requireContext())
        dataInit(requireContext())
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        announcementRecyclerView = view.findViewById(R.id.dashboard_announcements_recycler_view)
        newRecyclerView = view.findViewById(R.id.dashboard_courses)
        Handler(Looper.getMainLooper()).postDelayed({
            progressDialog.dismiss()
        }, 500)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun dataInit(context:Context){
        courseArrayList = arrayListOf<CoursesModel>()

        val token : String? = User.getLoginCredentials(context)
        val credentials = token?.let { User.getUsernamePassword(it) }
        val u : String = credentials?.first ?: ""
        val p : String = credentials?.second ?: ""

        if(u == "" && p == "") return

        val retrofit = RetrofitClientInstance(u,p).getRetrofitInstance()
        val api = retrofit.create(InterfaceAPI::class.java)
        val currToken = User.getLoginCredentials(context)
        val userCall = currToken?.let { api.getUser(it) }

        userCall?.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful) {
                    response.body()?.let { fetchData(it, api, currToken) }
                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("J COLE", t.localizedMessage)
            }
        })
    }

    fun makeAnnouncements(announcements : ArrayList<AnnouncementModel>){
        announcementList = announcements
        var singleLayoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        announcementList = announcements
        announcementsAdapter = AnnouncementsAdapter(announcementList)
        announcementRecyclerView.adapter = announcementsAdapter
        announcementRecyclerView.isNestedScrollingEnabled = false
        announcementRecyclerView.layoutManager = singleLayoutManager
        Log.d("J COLE", announcementList.toString())
    }

    fun makeCourses(courses : ArrayList<CoursesModel>){
        courseArrayList = courses
        var doubleLayoutManager =  StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        adapter = MyAdapter(courseArrayList, this)
        newRecyclerView.adapter = adapter
        newRecyclerView.isNestedScrollingEnabled = false
        newRecyclerView.layoutManager = doubleLayoutManager
    }

    fun fetchData(currUser : User, api : InterfaceAPI, token : String){
        user = currUser
        val announcementCall = api.getAnnouncements(token)
        val courseCall = api.getSubjects(token, user.grade.toString())
        announcementCall.enqueue(object : Callback<ArrayList<AnnouncementModel>> {
            override fun onResponse(
                call: Call<ArrayList<AnnouncementModel>>,
                response: Response<ArrayList<AnnouncementModel>>
            ) {
                if(response.isSuccessful){
                    val announcements : ArrayList<AnnouncementModel>? = response.body()
                    if (announcements != null) {
                        makeAnnouncements(announcements.take(5) as ArrayList<AnnouncementModel>)
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<AnnouncementModel>>, t: Throwable) {
                Log.e("J COLE", t.localizedMessage)
            }
        })
        courseCall.enqueue(object : Callback<ArrayList<CoursesModel>>{
            override fun onResponse(
                call: Call<ArrayList<CoursesModel>>,
                response: Response<ArrayList<CoursesModel>>
            ) {
                if(response.isSuccessful){
                    val courses : ArrayList<CoursesModel>? = response.body()
                    if (courses != null) {
                        makeCourses(courses as ArrayList<CoursesModel>)
                    }
                }
            }
            override fun onFailure(call: Call<ArrayList<CoursesModel>>, t: Throwable) {
                Log.e("J COLE", t.localizedMessage)
            }
        })
    }

    override fun onItemClick(itemId: String) {
        val intent = Intent(activity, SubjectActivity::class.java)
        intent.putExtra("ITEM_ID", itemId)
        startActivity(intent)
    }
}