package com.example.itcp.adapters

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.itcp.R
import com.example.itcp.models.CoursesModel
import kotlin.random.Random
import android.content.Context
import com.squareup.picasso.*
import okhttp3.OkHttpClient

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
        val bg : RelativeLayout = itemView.findViewById(R.id.row_background)
        val cover : RelativeLayout = itemView.findViewById(R.id.img_container)

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
        val imgFile = currentItem.subj_image.replace(" ", "%20")
        holder.courseName.text = currentItem.subj_name
        holder.codeTextView.text = currentItem.subj_code
        holder.deptTitle.text = if(currentItem.dept != "") currentItem.dept else "N/A"
        val imageUrl = "https://smshs-capstone.000webhostapp.com/imgsubject/$imgFile"
        val drawables = arrayOf(R.drawable.crit, R.drawable.stats, R.drawable.ep1,
            R.drawable.twentyfirstst, R.drawable.fil1)
        Log.d("J COLE", imageUrl)
        val random = Random
        val colors = arrayOf("#7D5A50", "#FF2E63", "#B83B5E", "#3F72AF", "#967E76")
        val randomNumberInRange = random.nextInt(0, 4) // generates a random number between 5 and 14
        holder.bg.setBackgroundColor(Color.parseColor(colors[randomNumberInRange]))
        holder.bind(currentItem, listener)
        holder.cover.setBackgroundResource(drawables[randomNumberInRange])

        Picasso.get()
            .load(imageUrl)
            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
            .networkPolicy(NetworkPolicy.OFFLINE)
            .into(object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                holder.cover.background = BitmapDrawable(holder.itemView.resources, bitmap)
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                Log.e("J COLE", e.toString())
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                Log.d("J COLE", imageUrl)
            }
        })
    }

    override fun getItemCount(): Int {
        return courseList.size
    }
}