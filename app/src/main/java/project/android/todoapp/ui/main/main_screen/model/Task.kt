package project.android.todoapp.ui.main.main_screen.model

import project.android.todoapp.model.Tag
import project.android.todoapp.model.TaskState
import java.util.*

data class Task(
    val id : Int,
    val title : String,
    val tag : Tag,
    val content : String,
    val status : TaskState,
    val date : Date?
)