package project.android.todoapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import project.android.todoapp.model.Tag
import project.android.todoapp.model.Task
import project.android.todoapp.model.TaskState
import project.android.todoapp.repository.TaskRepository
import project.android.todoapp.ui.main.main_screen.model.TaskUI
import timber.log.Timber
import java.util.*

class TaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {
    private var listDisplayM = MutableLiveData<List<Task>>()
    val listDisplay: LiveData<List<Task>> get() = listDisplayM
    private var listAllTaskM = MutableLiveData<List<Task>>()
    val listAllTask: LiveData<List<Task>> get() = listAllTaskM
    private var listAllTaskOfProjectM = MutableLiveData<List<Task>>()
    val listAllTaskOfProject: LiveData<List<Task>> get() = listAllTaskM
    private var numberTaskCompleteM = MutableLiveData<Int>()
    val numberTaskComplete : LiveData<Int> get() = numberTaskCompleteM
    private var taskDetailDisplayM = MutableLiveData<Task>()
    val taskDetailDisplay: LiveData<Task> get() = taskDetailDisplayM

    init {
    }

    fun setTaskDetailDisplay(taskUI: Task) {
        taskDetailDisplayM.postValue(taskUI)
    }

    fun insertTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.insertTask(task)
            getAllTaskWithState()
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.updateTask(task)
            getAllTaskWithState()
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.deleteTask(task)
            getAllTaskOfProject(1)
            getAllTaskWithState()
        }

    }

    fun getAllTask() {
        viewModelScope.launch(Dispatchers.IO) {
            listAllTaskM.postValue(
                taskRepository.getAllTaskOfProject(1)
            )
        }
//        Timber.d("Go Here ${listAllTask.value == null}")
//        listAllTaskM.postValue(mutableListOf(
//            TaskUI(1,"Code Complete ToDoApp",Tag.KOTLIN_NATIVE,"Android Code", TaskState.DONE,Date()),
//            TaskUI(2,"Code Complete ToDoApp",Tag.KOTLIN_JS,"Android Code", TaskState.DONE,Date()),
//            TaskUI(3,"Code Complete ToDoApp",Tag.JETPACK,"Android Code", TaskState.DONE,Date()),
//            TaskUI(4,"Code Complete ToDoApp",Tag.JETPACK,"Android Code", TaskState.DONE,Date()),
//            TaskUI(5,"Code Complete ToDoApp",Tag.JETPACK,"Android Code", TaskState.DONE,Date()),
//            TaskUI(6,"Code Complete ToDoApp",Tag.JETPACK,"Android Code", TaskState.DONE,Date()),
//            TaskUI(7,"Code Complete ToDoApp",Tag.JETPACK,"Android Code", TaskState.DONE,Date()),
//            TaskUI(8,"Code Complete ToDoApp",Tag.JETPACK,"Android Code", TaskState.DONE,Date()),
//            TaskUI(79,"Code Complete ToDoApp",Tag.JETPACK,"Android Code", TaskState.DONE,Date())
//        ))
        listDisplayM.postValue(listAllTask.value)
    }

    fun getAllTaskOfProject(projectID: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            listDisplayM.postValue(taskRepository.getAllTaskOfProject(1))
            getAllTaskWithState()
        }
    }

    private fun getAllTaskWithState() {
        viewModelScope.launch(Dispatchers.IO) {
            val taskDone = taskRepository.getAllTaskWithState("DONE")
            withContext(Dispatchers.Main) {
                Timber.d("Task Done $taskDone")
                numberTaskCompleteM.postValue((taskDone*100)/ listDisplayM.value!!.size)
            }
        }
    }
}