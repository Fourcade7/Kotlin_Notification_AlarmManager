package com.pr7.alarm

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    val CHANNEL_ID = "CHANNEL_1"
    val NOTIFICATION_ID = 2
    lateinit var  pendingIntent:PendingIntent
    lateinit var alarmManager:AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannel()

        button1.setOnClickListener {
            val calendar=Calendar.getInstance()
            var hour=calendar.get(Calendar.HOUR_OF_DAY)
            var minut=calendar.get(Calendar.MINUTE)
            var second=calendar.get(Calendar.SECOND)

            val timeSetListener:TimePickerDialog.OnTimeSetListener=TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                textview1.setText("$hourOfDay : $minute")
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);
                val mills: Long = calendar.getTimeInMillis()

            val intent=Intent(this@MainActivity,AlarmBroadcastReceiver::class.java)
            pendingIntent=PendingIntent.getBroadcast(applicationContext,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)

            alarmManager=getSystemService(ALARM_SERVICE) as AlarmManager
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,mills,1*60*1000,pendingIntent)
             //24*60*60*1000
            }

            val timerpickerdialog=TimePickerDialog(this@MainActivity,R.style.ThemeOverlay_MaterialComponents_TimePicker,timeSetListener,hour,minut,true)
            timerpickerdialog.show()
        }

        button2.setOnClickListener {
            pendingIntent=PendingIntent.getBroadcast(applicationContext,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
            alarmManager=getSystemService(ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)

        }



    }

    fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Personal Notification"
            val descriptionText = "channel_description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        //Simple notification

    }

}