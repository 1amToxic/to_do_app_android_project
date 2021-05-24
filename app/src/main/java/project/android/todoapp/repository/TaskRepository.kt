package project.android.todoapp.repository

import androidx.lifecycle.LiveData
import project.android.todoapp.model.Task
import project.android.todoapp.storage.dao.TaskDao

class TaskRepository(private val taskDao: TaskDao) {
    suspend fun insertTask(task : Task){
        taskDao.insertTask(task)
    }
    suspend fun updateTask(task: Task){
        taskDao.updateTask(task)
    }
    suspend fun deleteTask(task: Task){
        taskDao.deleteTask(task)
    }
    suspend fun getAllTask() : List<Task>
        = taskDao.getAllTask()
    suspend fun getAllTaskOfProject(projectID : Int) : List<Task>
        = taskDao.getAllTaskOfProject(projectID)
    suspend fun getAllTaskWithState(state : String) : Int
        = taskDao.getAllTaskWithState(state)
}