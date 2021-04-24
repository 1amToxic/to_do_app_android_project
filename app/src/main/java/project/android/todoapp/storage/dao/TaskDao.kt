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
    fun getAllTask() : LiveData<List<Task>>
    @Query("SELECT * FROM task WHERE project_id = :projectID")
    fun getAllTaskOfProject(projectID : Int) : LiveData<List<Task>>
    @Query("SELECT COUNT(id) FROM task WHERE state = :state")
    suspend fun getAllTaskWithState(state : String) : Int
}