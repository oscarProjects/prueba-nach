package com.oscarvj.pruebanach.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.oscarvj.pruebanach.R
import com.oscarvj.pruebanach.data.firebase.db.FireBase
import javax.inject.Inject

class Locations @Inject constructor() {

    @Inject
    lateinit var fireBase: FireBase

    private lateinit var locationCallback: LocationCallback

    @SuppressLint("MissingPermission")
     fun getLocation(context: Context) {
        val locationRequest = LocationRequest.create().apply {
            interval = 60000 * 5
            fastestInterval = 60000 * 5
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                p0 ?: return
                for (location in p0.locations){
                    fireBase.registerLocation(location.latitude, location.longitude)
                    generateNotification(context, location)
                }
            }
        }
        val fusedLocationClient = FusedLocationProviderClient(context)
        fusedLocationClient.requestLocationUpdates(locationRequest,
            locationCallback,
            Looper.getMainLooper())

    }

    private fun generateNotification(context: Context, location: Location) {
        val channelId = context.getString(R.string.channel_id)

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.googleg_standard_color_18)
            .setContentTitle(context.getString(R.string.title_notification))
            .setContentText(context.getString(R.string.text_notification, location.latitude.toString(), location.longitude.toString()))
            .setSubText(context.getString(R.string.sub_text_notification))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        with(NotificationManagerCompat.from(context)) {
            notify(1, notification)
        }
    }

}