package com.oscarvj.pruebanach.ui.view.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.oscarvj.pruebanach.R
import com.oscarvj.pruebanach.constants.Constants
import com.oscarvj.pruebanach.di.manager.NavigationManager
import com.oscarvj.pruebanach.utils.LPermissions
import com.oscarvj.pruebanach.utils.Locations
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import android.content.Intent
import android.graphics.Bitmap


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    @Inject
    lateinit var navigation: NavigationManager

    @Inject
    lateinit var locations: Locations

    lateinit var lPermissions: LPermissions

    private lateinit var onCallPermission: OnCallPermission

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lPermissions = LPermissions(this)

        val extras = intent.extras
        val bundle = bundleOf(Constants.BUNDLE_INTERNET to getExtras(extras!!))

        navController = findNavController(R.id.fragment_host_main)
        navController.setGraph(R.navigation.nav_main, bundle)
        navigation.onNavigateComponents(navController)

        createNotificationChannel()

        if (lPermissions.location()) {
            locations.getLocation(this)
        }
    }

    private fun getExtras(extras: Bundle): Boolean {
        return extras.getBoolean(Constants.BUNDLE_INTERNET)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            when (requestCode) {
                Constants.REQUEST_LOCATION -> {
                    permissionSuccess(true, requestCode)
                }
                Constants.REQUEST_IMAGE_CAPTURE -> {
                    permissionSuccess(true, requestCode)
                }
            }
        } else {
            permissionSuccess(false, requestCode)
        }
    }

    private fun permissionSuccess(boolean: Boolean, requestCode: Int) {
        if (boolean) {
            locations.getLocation(this)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val channelId = getString(R.string.channel_id)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }

            val nm: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.createNotificationChannel(channel)
        }
    }

    interface OnCallPermission {
        fun permissionSuccess(boolean: Boolean)
    }

    fun setActivityListener(onCallPermission: OnCallPermission) {
        this.onCallPermission = onCallPermission
    }

}