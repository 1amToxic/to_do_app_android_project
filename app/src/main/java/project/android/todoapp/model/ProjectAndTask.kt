package project.android.todoapp.model

import androidx.room.Embedded
import androidx.room.Relation

data class ProjectAndTask(
    @Embedded val project: Project,
    @Relation(
        parentColumn = "id",
        entityColumn = "project_id"
    )
    val listTask : List<Task>
)