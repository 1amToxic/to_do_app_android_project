package project.android.todoapp.storage.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import project.android.todoapp.model.Task
@Dao
interface TaskDao {
    @Insert
    suspend fun insertTask(task : Task)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTask(task: Task)
    @Delete
    suspend fun deleteTask(task: Task)
    @Query("SELECT * FROM task")
    fun getAllTask() : List<Task>
    @Query("SELECT * FROM task WHERE project_id = :projectID")
    fun getAllTaskOfProject(projectID : Int) : List<Task>
    @Query("SELECT COUNT(id) FROM task WHERE state = :state")
    suspend fun getAllTaskWithState(state : String) : Int
    @Query("SELECT * FROM task WHERE project_id = :projectID order by title")
    fun getAllTaskOfProjectSortByName(projectID : Int) : List<Task>
    @Query("SELECT * FROM task WHERE project_id = :projectID order by deadline")
    fun getAllTaskOfProjectSortByDeadline(projectID : Int) : List<Task>
}