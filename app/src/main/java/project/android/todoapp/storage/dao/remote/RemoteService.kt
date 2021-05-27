package project.android.todoapp.storage.dao.remote

import project.android.todoapp.model.Account
import project.android.todoapp.model.Project
import project.android.todoapp.model.Task
import retrofit2.Call
import retrofit2.http.*

interface RemoteService {
    @GET("/account")
    fun getAllAccount() : Call<List<Account>>
    @POST("/account")
    fun addAccount(@Body account: Account) : Call<Account>
    @GET("/project")
    fun getAllProject() : Call<List<Project>>
    @POST("/project")
    fun addProject(@Body project : Project) : Call<Project>
    @GET("/task/project/{id}")
    fun getAllTaskOfProject(@Path("id") projectId : Int) : Call<List<Task>>
    @POST("/task")
    fun addTask(@Body task : Task) : Call<Task>
    @POST("/task/edit/{id}")
    fun editTask(@Path("id") taskId : Int,@Body task : Task) : Call<Task>
    @DELETE("/task/delete/{id}")
    fun deleteTask(@Path("id") taskId : Int) : Call<Task>
}