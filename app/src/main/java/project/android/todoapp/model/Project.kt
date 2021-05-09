package project.android.todoapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "project")
data class Project(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "account_id")
    val accountId : Int,
    val name: String,
    val description: String
)