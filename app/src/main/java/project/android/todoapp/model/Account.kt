package project.android.todoapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account")
data class Account(
    @PrimaryKey val id : Int,
    val email : String,
    val password : String
)