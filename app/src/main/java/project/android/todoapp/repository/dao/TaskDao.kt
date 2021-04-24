package project.android.todoapp.repository.dao

import androidx.room.*
import project.android.todoapp.model.ProjectAndTask
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
    fun getAllTask() : List<Task>?
    @Transaction
    @Query("SELECT * FROM task WHERE project_id = :projectID")
    suspend fun getAllTaskOfProject(projectID : Int) : List<Task>
}