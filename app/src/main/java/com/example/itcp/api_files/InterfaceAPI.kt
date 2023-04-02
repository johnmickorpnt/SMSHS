package com.example.itcp.api_files

import com.example.itcp.data_classes.LoginResponse
import com.example.itcp.data_classes.User
import com.example.itcp.models.AnnouncementModel
import com.example.itcp.models.CoursesModel
import com.example.itcp.models.ModuleModel
import retrofit2.Call
import retrofit2.http.*

interface InterfaceAPI {
    @FormUrlEncoded
    @POST("api/auth/login.php")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("api/auth/user.php")
    fun getUser(@Header("Authorization") authorization : String) :
            Call<User>

    @GET("api/announcement/read.php")
    fun getAnnouncements(@Header("Authorization") authorization : String) :
            Call<ArrayList<AnnouncementModel>>

    @GET("api/subject/read.php")
    fun getSubjects(@Header("Authorization") authorization: String, @Query("grade") grade: String) :
            Call<ArrayList<CoursesModel>>

    @GET("api/subject/get.php")
    fun getSubject(@Header("Authorization") authorization: String, @Query("id") id: String) :
            Call<CoursesModel>

    @GET("api/module/read.php")
    fun getModules(@Header("Authorization") authorization: String, @Query("id") id: String) :
            Call<ArrayList<ModuleModel>>
}
