package com.example.itcp.adapters

import android.app.DownloadManager
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Environment
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.itcp.R
import com.example.itcp.data_classes.User
import com.example.itcp.models.ModuleModel

class ModulesAdapter(private val modules: ArrayList<ModuleModel>, private val user : User, private val code : String) :
    RecyclerView.Adapter<ModulesAdapter.MyViewHolder>(){
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView: TextView = itemView.findViewById(R.id.moduleTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.modules_recycler_row, parent,
            false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return modules.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = modules[position]
        val spannableString = SpannableString(currentItem.name)
        val context = holder.itemView.context
        val id = currentItem.ID
        val userName = user.name
        val url = "http://sanmateoshs.infinityfreeapp.com/teachers/download.php?file_id=$id&name=$userName&sub_code=$code"
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Handle click action here
                downloadFile(context, url, currentItem.name)
            }
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                ds.color = ContextCompat.getColor(holder.itemView.context, R.color.link_color)
            }
        }

        spannableString.setSpan(clickableSpan, 0, currentItem.name.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        holder.textView.text = spannableString
        holder.textView.movementMethod = LinkMovementMethod.getInstance()
        holder.textView.highlightColor = Color.TRANSPARENT
    }

    private fun downloadFile(context: Context, url: String, filename : String) {
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri = Uri.parse(url)
        val request = DownloadManager.Request(uri)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename)
        downloadManager.enqueue(request)
    }
}

