package project.android.todoapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import project.android.todoapp.model.Task
import project.android.todoapp.repository.TaskRepository
import project.android.todoapp.ui.main.main_screen.model.TaskUI

class TaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {
    private var listDisplayM = MutableLiveData<List<TaskUI>>()
    val listDisplay : LiveData<List<TaskUI>> get() = listDisplayM
    private var listAllTaskM = MutableLiveData<List<TaskUI>>()
    val listAllTask : LiveData<List<TaskUI>> get() = listAllTaskM
    private var listAllTaskOfProjectM = MutableLiveData<List<Task>>()
    val listAllTaskOfProject : LiveData<List<TaskUI>> get() = listAllTaskM
    val numberTaskCompleteM : LiveData<Int>? = null
    fun insertTask(task : Task){
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.insertTask(task)
        }
    }
    fun updateTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.updateTask(task)
        }
    }
    fun deleteTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.deleteTask(task)
        }
    }
    fun getAllTask(){
        viewModelScope.launch(Dispatchers.IO) {
            listAllTaskM?.postValue(taskRepository.getAllTask().value?.map {
                TaskUI(
                    id = it.id,
                    content = it.description,
                    date = it.deadline,
                    status = it.state,
                    tag =  it.tag,
                    title = it.title
                )
            })
            listDisplayM?.postValue(listAllTask.value)
        }
    }
    suspend fun getAllTaskOfProject(projectID : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            listAllTaskOfProjectM?.postValue(taskRepository.getAllTaskOfProject(projectID).value)
            listDisplayM?.postValue(listAllTaskOfProject.value)
        }
    }
    suspend fun getAllTaskWithState(state : String) {
//        numberTaskComplete = taskRepository.getAllTaskWithState(state)
    }
}