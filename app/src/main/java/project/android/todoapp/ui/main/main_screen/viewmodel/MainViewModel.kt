package project.android.todoapp.ui.main.main_screen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.android.todoapp.ui.main.main_screen.model.TaskUI

class MainViewModel : ViewModel(){
    private var listTaskUI : MutableLiveData<List<TaskUI>>? = null
    val listTaskUIM : LiveData<List<TaskUI>> get() = listTaskUI!!

}