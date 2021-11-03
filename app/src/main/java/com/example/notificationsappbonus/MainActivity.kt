package com.example.notificationsappbonus

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var btShowNotification: Button
    lateinit var tvCounter:TextView
    private val channelId = "myapp.notifications"
    private val description = "Notification App Example"
    lateinit var builder: Notification.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btShowNotification = findViewById(R.id.btShowNotification)
        tvCounter=findViewById(R.id.tvCounter)
        btShowNotification.setOnClickListener {
            val timer = object: CountDownTimer(5000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    tvCounter.setText((millisUntilFinished/1000).toString())
                }
                override fun onFinish() {
                    tvCounter.setText("")
                    val notificationManager =
                        getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                    val intent = Intent(baseContext, CakeActivity::class.java)
                    val pendingIntent =
                        PendingIntent.getActivity(baseContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        var notificationChannel = NotificationChannel(
                            channelId,
                            description,
                            NotificationManager.IMPORTANCE_HIGH
                        )
                        notificationManager.createNotificationChannel(notificationChannel)

                        builder = Notification.Builder(baseContext, channelId)
                            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                            .setContentIntent(pendingIntent)
                            .setContentTitle("Cake Counting Timer")
                            .setContentText("Ready")
                    } else {
                        builder = Notification.Builder(baseContext)
                            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                            .setContentIntent(pendingIntent)
                            .setContentTitle("Cake Counting Timer")
                            .setContentText("Ready")
                    }
                    notificationManager.notify(1234, builder.build())

                }
            }
            timer.start()




        }
    }
}