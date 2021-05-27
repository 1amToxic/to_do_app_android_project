package project.android.todoapp.service


import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import project.android.todoapp.R
import project.android.todoapp.model.Task
import project.android.todoapp.ui.main.MainActivity

class AlarmReceiver: BroadcastReceiver() {
    companion object{
        private const val CHANNEL_CODE = "com.example.todoapp"
        private const val NOTIFICATION_ID = 4567
    }
    override fun onReceive(context: Context, intent: Intent) {
        // sendNotification
//        val bundle = intent.extras
        val title : String? = intent.getStringExtra("title")
        val des : String? = intent.getStringExtra("des")
        val intentForward = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intentForward, 0)
        val builder = NotificationCompat.Builder(context, CHANNEL_CODE)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle(title)
            .setContentText(des)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("ToDoForDev")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(43, builder.build())
        }
    }

}