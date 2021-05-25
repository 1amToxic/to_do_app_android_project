package project.android.todoapp.service

import android.app.*
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import project.android.todoapp.R
import project.android.todoapp.model.Task
import project.android.todoapp.ui.main.MainActivity
import project.android.todoapp.utils.Converters

class NotificationBuilder(private val application: Application){
    init {
        createNotificationChannel()
    }
    companion object{
        private const val NOTIFICATION_ID = 4567
        private const val CHANNEL_CODE = "com.example.todoapp"
    }
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "test_channel"
            val descriptionText = "this is my test channel"

            //other Importance types are
            //IMPORTANCE_HIGH
            //IMPORTANCE_LOW
            //IMPORTANCE_MAX
            //IMPORTANCE_MIN
            val importance = IMPORTANCE_HIGH

            //define your own channel code here i used a predefined constant
            val channel = NotificationChannel(CHANNEL_CODE, name, importance).apply {
                description = descriptionText
            }

            // Register the channel with the system
            // I am using application class's context here
            val notificationManager: NotificationManager =
                application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun createNotification(context: Context, task : Task) {

        val intentSchedule = Intent(context,AlarmReceiver::class.java)
        val bundle = Bundle()
        bundle.putSerializable("task",task)
        intentSchedule.putExtras(bundle)
        val pendingSchedule = PendingIntent.getBroadcast(context,43,intentSchedule,PendingIntent.FLAG_UPDATE_CURRENT)
        val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        Converters.dateToTimestamp(task.deadline)?.let {
            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                it,pendingSchedule)
        }

    }
}