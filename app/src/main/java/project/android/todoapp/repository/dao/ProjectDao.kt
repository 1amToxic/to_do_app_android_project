package project.android.todoapp.repository.dao

import androidx.room.Insert
import androidx.room.Query
import project.android.todoapp.model.Project

interface ProjectDao {
    @Insert
    fun insertProject(project: Project)
    @Query("SELECT * FROM project")
    fun getAllProject() : List<Project>
}