package com.example.sampleapp.presentation.ui.fragment

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.sampleapp.R
import com.example.sampleapp.data.local.AppPreferences
import java.security.Permission
import javax.inject.Inject

open class BaseFragment : Fragment() {
    @Inject
    lateinit var appPref: AppPreferences
    private var permissionCallback: ((Boolean) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun handleCameraPermission(callback: (Boolean) -> Unit) {
        permissionCallback = callback
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                callback.invoke(true)
            }

            appPref.perCamera == 0 || appPref.perCamera == 1 -> {
                cameraPermissionRequestLauncher.launch(Manifest.permission.CAMERA)
            }
            else -> {
                showRationaleDialog()
            }
        }
    }

    private val cameraPermissionRequestLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if(!isGranted){
                appPref.perCamera++
            }
            permissionCallback?.invoke(isGranted)
        }

    private fun showRationaleDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Camera Permission Required")
            .setMessage("This app needs access to your camera to take photos. Please grant the permission.")
            .setPositiveButton("OK") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:" + context?.packageName)
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
                permissionCallback?.invoke(false)
            }
            .create()
            .show()
    }
}