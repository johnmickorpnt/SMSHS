package com.example.itcp.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.itcp.R
import com.example.itcp.models.AnnouncementModel

class AnnouncementsAdapter(private val annoucementList: ArrayList<AnnouncementModel>) :
    RecyclerView.Adapter<AnnouncementsAdapter.MyViewHolder>() {


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val announcementTitle: TextView = itemView.findViewById(R.id.announcementTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.dashboard_announcement_view_row, parent,
        false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return annoucementList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = annoucementList[position]
        holder.announcementTitle.text = currentItem.title
        holder.itemView.setOnClickListener{
            Toast.makeText(holder.itemView.context, "haha", Toast.LENGTH_SHORT).show()
        }
    }

}