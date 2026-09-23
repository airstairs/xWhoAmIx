package com.xthan.whoami

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class DeviceNameService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val prefs = getSharedPreferences("DevicePrefs", MODE_PRIVATE)
        val num = prefs.getString("device_num", "00") ?: "00"
        val formattedName = "x${num}x"

        val channelId = "device_name_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Device Name", NotificationManager.IMPORTANCE_LOW)
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Device Identifier")
            .setContentText(formattedName)
            .setSmallIcon(R.drawable.ic_x) // Uses our custom lowercase x icon
            .setOngoing(true)
            .build()

        startForeground(1, notification)
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null
}