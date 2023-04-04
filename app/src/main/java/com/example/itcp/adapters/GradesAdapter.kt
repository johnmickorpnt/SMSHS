package com.example.itcp.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.itcp.R
import com.example.itcp.models.GradeModel
import kotlin.random.Random

class GradesAdapter(private val gradesList : ArrayList<GradeModel>) :
    RecyclerView.Adapter<GradesAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val gradeText : TextView = itemView.findViewById(R.id.grade_value)
        val courseTitle : TextView = itemView.findViewById(R.id.courseTitle)
        val bg : RelativeLayout = itemView.findViewById(R.id.grade_relativelayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GradesAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.grades_recycler_row, parent,
            false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = gradesList[position]
        holder.gradeText.text = currentItem.grade
        holder.courseTitle.text = currentItem.subjectName
        val random = Random
        val colors = arrayOf("#7D5A50", "#FF2E63", "#B83B5E", "#3F72AF", "#967E76")
        val randomNumberInRange = random.nextInt(0, 4) // generates a random number between 5 and 14
        holder.bg.setBackgroundColor(Color.parseColor(colors[randomNumberInRange]))
    }

    override fun getItemCount(): Int {
        return gradesList.size
    }
}