package project.android.todoapp.repository.dao

import androidx.room.*
import project.android.todoapp.model.Task

interface TaskDao {
    @Insert
    fun insertTask(task : Task)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTask(task: Task)
    @Delete
    fun deleteTask(task: Task)
    @Query("SELECT * FROM task")
    fun getAllTask() : List<Task>?
    @Query("SELECT * FROM task WHERE project_id = :projectID")
    fun getAllTaskOfProject(projectID : Int) : List<Task>?
}