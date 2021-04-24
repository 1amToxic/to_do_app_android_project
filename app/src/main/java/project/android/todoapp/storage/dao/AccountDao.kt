package project.android.todoapp.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import project.android.todoapp.model.Account

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account : Account)
    @Query("SELECT * FROM account WHERE email = :email AND password = :password")
    fun checkAccountAvailable(email : String, password : String) : Account?
}