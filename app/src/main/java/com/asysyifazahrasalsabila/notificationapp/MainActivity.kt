package com.asysyifazahrasalsabila.notificationapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSend.setOnClickListener {
            val msg = edtMsg.text.toString()

            val intent = Intent(this, ReceiverActivity::class.java)
            intent.putExtra("msg", msg)
            val pIntent = PendingIntent.getActivity(this, System.currentTimeMillis().toInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val notif = NotificationCompat.Builder(this, "channelID")
                .setContentTitle("You've got a message")
                .setContentText(msg)
                .setSmallIcon(R.drawable.icon)
                .setContentIntent(pIntent)
                .setAutoCancel(true)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val channelID = "ChannelID"
                val channel = NotificationChannel(channelID, "notif", NotificationManager.IMPORTANCE_HIGH)

                val notificationManager = this.getSystemService(NotificationManager::class.java)
                notificationManager.createNotificationChannel(channel)
                notif.setChannelId(channelID)
            }

            val notificationManager = NotificationManagerCompat.from(this)
            notificationManager.notify(0, notif.build())

            edtMsg.setText("")

        }
    }
}
