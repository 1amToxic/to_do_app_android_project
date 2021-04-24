package project.android.todoapp.storage.database

import android.content.Context
import androidx.room.*
import project.android.todoapp.model.Account
import project.android.todoapp.model.Project
import project.android.todoapp.model.Task
import project.android.todoapp.storage.dao.AccountDao
import project.android.todoapp.storage.dao.ProjectDao
import project.android.todoapp.storage.dao.TaskDao
import project.android.todoapp.utils.Converters

@Database(entities = [Account::class, Project::class, Task::class],version = 1,exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun accountDao() : AccountDao
    abstract fun projectDao() : ProjectDao
    abstract fun taskDao() : TaskDao
    companion object{
        @Volatile
        private var instance : AppDatabase? = null
        fun getDatabase(context: Context) : AppDatabase{
            if(instance == null){
                synchronized(this){
                    instance = Room
                        .databaseBuilder(context,AppDatabase::class.java,"to_do")
                        .build()
                }
            }
            return instance!!
        }
    }
}