package project.android.todoapp.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import project.android.todoapp.model.Project
import project.android.todoapp.model.Task

@Dao
interface ProjectDao {
    @Insert
    suspend fun insertProject(project: Project)
    @Query("SELECT * FROM project")
    fun getAllProject() : List<Project>

}