package com.example.itcp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.itcp.R
import com.example.itcp.models.CoursesModel

interface OnItemClickListener {
    fun onItemClick(itemId: String)
}

class MyAdapter(private val courseList: ArrayList<CoursesModel>,
                private val listener: OnItemClickListener) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val courseName : TextView = itemView.findViewById(R.id.courseTitle)
        val codeTextView : TextView = itemView.findViewById(R.id.code_title)
        val deptTitle : TextView = itemView.findViewById(R.id.dept_title)
        fun bind(item: CoursesModel, listener: OnItemClickListener){
            itemView.setOnClickListener { listener.onItemClick(item.subj_id) }
        }
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_view_row, parent,
            false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = courseList[position]
        holder.courseName.text = currentItem.subj_name
        holder.codeTextView.text = currentItem.subj_code
        holder.deptTitle.text = currentItem.dept
        holder.bind(currentItem, listener)
    }

    override fun getItemCount(): Int {
        return courseList.size
    }
}