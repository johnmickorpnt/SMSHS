package com.example.itcp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.itcp.R
import com.example.itcp.models.CoursesModel

class MyAdapter(private val courseList: ArrayList<CoursesModel>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    private lateinit var mListener: onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    class MyViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        val courseName : TextView = itemView.findViewById(R.id.courseTitle)
        val statusName : TextView = itemView.findViewById(R.id.status)

        init{
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_view_row, parent,
            false)
        return MyViewHolder(itemView, mListener)
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = courseList[position]
        holder.courseName.text = currentItem.subj_name
        holder.statusName.text = currentItem.grade
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "haha", Toast.LENGTH_SHORT).show()
        }
    }
}