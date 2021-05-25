package project.android.todoapp.storage.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import project.android.todoapp.model.Project

@Dao
interface ProjectDao {
    @Insert
    suspend fun insertProject(project: Project)
    @Query("SELECT * FROM project")
    fun getAllProject() : LiveData<List<Project>>
    @Query("SELECT * FROM project where id = :idP")
    fun getProjectById(idP : Int): Project
}