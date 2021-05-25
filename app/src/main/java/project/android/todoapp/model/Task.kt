package project.android.todoapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val description: String,
    var state: TaskState,
    val tag: Tag,
    @ColumnInfo(name = "project_id")
    val projectId : Int,
    val deadline : Date? = null
) : Serializable