package project.android.todoapp.repository

import androidx.lifecycle.LiveData
import project.android.todoapp.model.Project
import project.android.todoapp.storage.dao.ProjectDao

class ProjectRepository(val projectDao: ProjectDao) {
    suspend fun insertProject(project: Project) {
        projectDao.insertProject(project)

    }

    suspend fun getAllProject(): LiveData<List<Project>> = projectDao.getAllProject()
}