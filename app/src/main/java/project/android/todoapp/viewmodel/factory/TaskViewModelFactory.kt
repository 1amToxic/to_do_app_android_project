package project.android.todoapp.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import project.android.todoapp.ToDoApplication
import project.android.todoapp.repository.TaskRepository
import project.android.todoapp.viewmodel.TaskViewModel

class TaskViewModelFactory (val context: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val taskDao = ToDoApplication.todoDb.taskDao()
        val taskRepository = TaskRepository(taskDao)
        return TaskViewModel(taskRepository)  as T
    }

}