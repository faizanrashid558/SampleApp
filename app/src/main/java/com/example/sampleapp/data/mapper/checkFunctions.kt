package com.example.sampleapp.data.mapper

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import com.example.sampleapp.data.constants.Constants.CAMERA_PERMISSION
import com.example.sampleapp.data.constants.Constants.LOCATION_PERMISSION
import com.example.sampleapp.data.constants.Constants.READ_WRITE_EXTERNAL_PERMISSION

private var permissionCallback: ((Boolean) -> Unit)? = null

// Camera, RecordAudio
fun Context.checkPerCamera(): Boolean {
    return CAMERA_PERMISSION.all { permission ->
        ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }
}

// Location Permission
fun Context.checkPerLoc(): Boolean {
    return LOCATION_PERMISSION.all { permission ->
        ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }
}

//Android 13 Above- READ_MEDIA_IMAGES, READ_MEDIA_VIDEO, READ_MEDIA_AUDIO
//Android 13 Below- Read External Storage, Write External Storage
fun Context.checkPerReadWrite(): Boolean {
    return READ_WRITE_EXTERNAL_PERMISSION.all { permission ->
        ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }
}

// Custom Permission
fun Context.checkPerCustom(permissions: Array<String>): Boolean {
    return permissions.all { permission ->
        ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }
}

fun Context.reqPerAll(permissions: Array<String>, prefName: Int, callback: (String) -> Unit) {
    when {
        permissions.all { permission ->
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
        } -> {
            callback.invoke("granted")
        }

        prefName == 0 || prefName == 1 -> {
            callback.invoke("launcher")
        }

        else -> {
            showRationaleDialog(callback)
        }
    }
}

private fun Context.showRationaleDialog(callback: (String) -> Unit) {
    AlertDialog.Builder(this)
        .setTitle("Camera Permission Required")
        .setMessage("This app needs access to your camera to take photos. Please grant the permission.")
        .setPositiveButton("OK") { dialog, _ ->
            callback("setting_ok")
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:$packageName")
            startActivity(intent)
            dialog.dismiss()
        }
        .setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
            callback("setting_cancel")
        }
        .create()
        .show()
}





