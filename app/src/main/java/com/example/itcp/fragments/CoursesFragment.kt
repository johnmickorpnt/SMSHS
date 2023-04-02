package com.example.itcp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.itcp.R
import com.example.itcp.adapters.MyAdapter
import com.example.itcp.models.CoursesModel

class CoursesFragment : Fragment() {
    // TODO: Rename and change types of parameters

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
//        var layoutManager =  StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
//        newRecyclerView = view.findViewById(R.id.recycler_view)
//        adapter = MyAdapter(courseArrayList)
//        adapter.setOnItemClickListener(object : MyAdapter.onItemClickListener{
//            override fun onItemClick(position: Int) {
//                val currTitle = title[position]
//
//                replaceFragment(CourseFragment())
//                Toast.makeText(context, "You clicked item $currTitle", Toast.LENGTH_SHORT).show()
//            }
//        })
//        newRecyclerView.adapter = adapter
//        newRecyclerView.isNestedScrollingEnabled = false
//        newRecyclerView.layoutManager = layoutManager

    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        if (fragmentTransaction != null) {
            fragmentTransaction.replace(R.id.frameLayout, fragment)
        }

        if (fragmentTransaction != null) {
            fragmentTransaction.commit()
        }
    }
    
    private fun dataInit(){
//        val retrofit = RetrofitClientInstance.getRetrofitInstance()
//        val api = retrofit.create(InterfaceAPI::class.java)
//
//        val call = api.getCourses()
//
//        call.enqueue(object : Callback<List<CoursesModel>> {
//            override fun onResponse(call: Call<List<CoursesModel>>, response: Response<List<CoursesModel>>) {
//                if (response.isSuccessful) {
//                    val courses : List<CoursesModel>? = response.body()
//                    Log.d("API Response Body", courses.toString())
//
//                    // TODO: display the posts in the RecyclerView
//                } else {
//                    // TODO: handle error
//                    Log.d("API Response Body", "ERROR")
//                }
//            }
//
//            override fun onFailure(call: Call<List<CoursesModel>>, t: Throwable) {
//                // TODO: handle error
//                Log.d("API Response Body", t.localizedMessage)
//            }
//        })

//        title = arrayOf(
//            "IT 001",
//            "IT 002",
//            "IT 203",
//            "ITE 404",
//            "IT 005",
//            "ITE 106",
//            "ITE 404",
//            "IT 005",
//            "ITE 106",
//        )
//        status = arrayOf(
//            "N/A",
//            "100%",
//            "50%",
//            "70%",
//            "80%",
//            "NA",
//            "70%",
//            "80%",
//            "NA",
//        )
//        for(i in title.indices){
//            val course = CoursesModel()
//            courseArrayList.add(course)
//        }
    }
}