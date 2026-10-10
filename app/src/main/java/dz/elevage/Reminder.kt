package dz.elevage

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(ctx: Context, intent: Intent) {
        val label = intent.getStringExtra("label") ?: "ElevageDZ"
        val nm = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.createNotificationChannel(
            NotificationChannel("reminders", "Reminders", NotificationManager.IMPORTANCE_DEFAULT)
        )
        val n = NotificationCompat.Builder(ctx, "reminders")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("ElevageDZ")
            .setContentText(label)
            .setAutoCancel(true)
            .build()
        nm.notify(label.hashCode(), n)
    }
}

object Reminder {
    fun schedule(ctx: Context, label: String, triggerAtMillis: Long) {
        val am = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pi = PendingIntent.getBroadcast(
            ctx, label.hashCode(),
            Intent(ctx, ReminderReceiver::class.java).putExtra("label", label),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        try { am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pi) }
        catch (_: SecurityException) { am.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pi) }
    }
}
