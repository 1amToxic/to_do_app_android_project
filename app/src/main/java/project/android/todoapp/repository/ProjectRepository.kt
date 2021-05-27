package project.android.todoapp.repository

import androidx.lifecycle.LiveData
import project.android.todoapp.model.Project
import project.android.todoapp.storage.dao.ProjectDao

class ProjectRepository(val projectDao: ProjectDao) {
    companion object{
        var instance : ProjectRepository? = null
        fun newInstance(projectDao : ProjectDao) : ProjectRepository{
            if(instance == null) {
                instance =  ProjectRepository(projectDao)
            }
            return instance!!
        }
    }
    suspend fun insertProject(project: Project) {
        projectDao.insertProject(project)

    }

    fun getAllProject(): List<Project> = projectDao.getAllProject()
    fun getProjectById(idP : Int) : Project = projectDao.getProjectById(idP)
}