package project.android.todoapp.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "project")
data class Project(
    @PrimaryKey val id: Int = 0,
    val name: String,
    val description: String,
    @Ignore val listTask : List<Task> = emptyList()
)