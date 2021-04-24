package project.android.todoapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity(tableName = "task")
data class Task(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String?,
    val state: TaskState,
    val tag: Tag?,
    @ColumnInfo(name = "project_id")
    val projectId : Int,
    val deadline : Date?
)