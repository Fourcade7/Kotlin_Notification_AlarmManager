package com.pr7.alarm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import android.content.Context.MODE_PRIVATE


class AlarmBroadcastReceiver : BroadcastReceiver() {

    val CHANNEL_ID = "CHANNEL_1"
    val NOTIFICATION_ID = 2

    lateinit var sharedpreferences: SharedPreferences
    var i = 0


    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, Date().toString(), Toast.LENGTH_SHORT).show()
        Log.d("Pr", Date().toString())
        load(context)
        if (i == 0) {
            i = 1
            save(context, i)
        } else {
            i++
            save(context, i)
        }

        val notification: Notification = NotificationCompat.Builder(context!!, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_brush_24)
            .setContentTitle("count")
            .setContentText("xaxaxaxaxa $i")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()
        val notificationManagerCompat = NotificationManagerCompat.from(context!!)
        notificationManagerCompat.notify(i, notification)
    }

    fun save(context: Context?, x: Int) {
        sharedpreferences = context!!.getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val editor = sharedpreferences.edit()
        editor.putInt("pr", x)
        editor.commit()

    }

    fun load(context: Context?) {
        sharedpreferences = context!!.getSharedPreferences("MySharedPref", MODE_PRIVATE)
        i = sharedpreferences.getInt("pr", 0)

    }


}