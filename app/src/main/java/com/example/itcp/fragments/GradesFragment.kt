package com.example.itcp.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.itcp.R
import com.example.itcp.adapters.GradesAdapter
import com.example.itcp.adapters.MyAdapter
import com.example.itcp.models.GradeModel

class GradesFragment : Fragment() {
    private lateinit var gradesAdapter: GradesAdapter
    private lateinit var gradesRecyclerView: RecyclerView
    private lateinit var gradeArrayList : ArrayList<GradeModel>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_grades, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInit()
        var layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        gradesRecyclerView = view.findViewById(R.id.grades_subject_recycleview)
        gradesAdapter = GradesAdapter(gradeArrayList)
        gradesRecyclerView.adapter = gradesAdapter
        gradesRecyclerView.isNestedScrollingEnabled = false
        gradesRecyclerView.layoutManager = layoutManager

    }
    fun dataInit(){
        gradeArrayList = arrayListOf<GradeModel>()

        val gradeList = arrayListOf(
            "90","98","89","99","92","95", "96", "95"
        )
        val subjectList = arrayListOf(
            "Gen Math",
            "Purposive Communication",
            "Physical Education and Health",
            "Personal Development",
            "Practical Research 1",
            "Business Math",
            "Organization and Management",
            "21st Century Literature from the Philippines and the World"
        )

        for(i in gradeList.indices){
            val grade = GradeModel(gradeList[i], subjectList[i])
            gradeArrayList.add(grade)
        }

    }
}