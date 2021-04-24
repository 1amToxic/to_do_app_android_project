package project.android.todoapp

import android.app.Application
import android.util.Log
import project.android.todoapp.storage.database.AppDatabase
import timber.log.Timber
import timber.log.Timber.DebugTree


class ToDoApplication : Application() {
    companion object{
        lateinit var todoDb : AppDatabase
    }
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
        todoDb = AppDatabase.getDatabase(applicationContext)
    }
}

class ReleaseTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if(priority == Log.VERBOSE || priority == Log.DEBUG){
            return
        }
    }
}
