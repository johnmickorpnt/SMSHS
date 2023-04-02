package com.example.itcp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.itcp.R
import com.example.itcp.models.CoursesModel

class CourseAdapter(private val courseList: ArrayList<CoursesModel>) :
    RecyclerView.Adapter<CourseAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val courseName : TextView = itemView.findViewById(R.id.courseTitle)
        val code : TextView = itemView.findViewById(R.id.dept_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_view_row, parent,
            false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = courseList[position]
        holder.courseName.text = currentItem.subj_name
        holder.code.text = currentItem.subj_code
    }
}