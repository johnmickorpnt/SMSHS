package com.example.itcp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class DashboardFragment : Fragment() {
    private lateinit var announcementsAdapter: AnnouncementsAdapter
    private lateinit var announcementList : ArrayList<AnnouncementModel>
    private lateinit var announcementTitles : Array<String>
    private lateinit var announcementRecyclerView : RecyclerView

    private lateinit var adapter: MyAdapter
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var courseArrayList : ArrayList<CoursesModel>

    private lateinit var title : Array<String>
    private lateinit var section : Array<String>
    private lateinit var status : Array<String>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInit()
        var singleLayoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        announcementRecyclerView = view.findViewById(R.id.dashboard_announcements_recycler_view)
        announcementsAdapter = AnnouncementsAdapter(announcementList)

        announcementRecyclerView.adapter = announcementsAdapter
        announcementRecyclerView.isNestedScrollingEnabled = false
        announcementRecyclerView.layoutManager = singleLayoutManager

        var doubleLayoutManager =  StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        newRecyclerView = view.findViewById(R.id.dashboard_courses)
        adapter = MyAdapter(courseArrayList)
        adapter.setOnItemClickListener(object : MyAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val currTitle = title[position]

//                replaceFragment(CourseFragment())
                Toast.makeText(context, "You clicked item $currTitle", Toast.LENGTH_SHORT).show()
            }
        })
        newRecyclerView.adapter = adapter
        newRecyclerView.isNestedScrollingEnabled = false
        newRecyclerView.layoutManager = doubleLayoutManager
    }

    private fun dataInit(){
        courseArrayList = arrayListOf<CoursesModel>()
        announcementList = arrayListOf<AnnouncementModel>()

        announcementTitles = arrayOf(
            "YOU TALK, WE LISTEN",
            "Just a little help",
            "Viewing of Canvas Courses",
            "Lenten Season 2023"
        )

        title = arrayOf(
            "IT 001",
            "IT 002",
            "IT 203",
            "ITE 404",
            "IT 005",
            "ITE 106",
            "ITE 404",
            "IT 005",
            "ITE 106",
        )

        section = arrayOf(
            "IT4A1",
            "IT4A2",
            "IT4A1",
            "IT4A2",
            "IT4A1",
            "IT4A3",
            "IT4A2",
            "IT4A1",
            "IT4A3"
        )

        status = arrayOf(
            "N/A",
            "100%",
            "50%",
            "70%",
            "80%",
            "NA",
            "70%",
            "80%",
            "NA",
        )


        for(i in announcementTitles.indices){
            val announcement = AnnouncementModel(announcementTitles[i], "Sample body")
            announcementList.add(announcement)
        }

        for(i in title.indices){
            val course = CoursesModel(title[i], section[i], status[i])
            courseArrayList.add(course)
        }
    }
}