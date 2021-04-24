package project.android.todoapp.ui.main.main_screen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.android.todoapp.ui.main.main_screen.model.Task

class MainViewModel : ViewModel(){
    private var listTask : MutableLiveData<List<Task>>? = null
    val listTaskM : LiveData<List<Task>> get() = listTask!!

}