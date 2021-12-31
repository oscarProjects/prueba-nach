package com.oscarvj.pruebanach.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.oscarvj.pruebanach.constants.Constants

class LPermissions(private val activity: Activity) {

    fun camera(): Boolean {
        return if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA), Constants.REQUEST_IMAGE_CAPTURE)
            false
        } else {
            true
        }
    }

    fun location(): Boolean {
        val r = Constants.REQUEST_LOCATION
        return if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                r
            )
            false
        } else {
            true
        }
    }
}
