package project.android.todoapp.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import project.android.todoapp.ToDoApplication
import project.android.todoapp.repository.ProjectRepository
import project.android.todoapp.viewmodel.ProjectViewModel

class ProjectViewModelFactory (val context: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val projectDao = ToDoApplication.todoDb.projectDao()
        val projectRepository = ProjectRepository(projectDao)
        return ProjectViewModel(projectRepository) as T
    }

}