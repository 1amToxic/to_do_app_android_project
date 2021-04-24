package project.android.todoapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import project.android.todoapp.model.Project
import project.android.todoapp.repository.ProjectRepository

class ProjectViewModel(private val projectRepository: ProjectRepository) : ViewModel() {
    private var listProjectM : MutableLiveData<List<Project>>? = null
    val listProject : LiveData<List<Project>> get() = listProjectM!!
    fun insertProject(project : Project){
        viewModelScope.launch(Dispatchers.IO) {
            projectRepository.insertProject(project)
        }
    }
    fun getAllProject(){
        viewModelScope.launch(Dispatchers.IO) {
            listProjectM?.postValue(projectRepository.getAllProject().value)
        }
    }
}
