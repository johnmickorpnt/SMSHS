package com.example.itcp

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class CoursesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        return inflater.inflate(R.layout.fragment_courses, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInit()
        var layoutManager =  StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        newRecyclerView = view.findViewById(R.id.recycler_view)
        adapter = MyAdapter(courseArrayList)
        newRecyclerView.adapter = adapter
        newRecyclerView.isNestedScrollingEnabled = false
        newRecyclerView.layoutManager = layoutManager

    }

    private fun dataInit(){
        courseArrayList = arrayListOf<CoursesModel>()

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

        for(i in title.indices){
            val course = CoursesModel(title[i], section[i], status[i])
            courseArrayList.add(course)
        }
    }
}