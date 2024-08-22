package com.example.sampleapp.data.constants

import android.Manifest
import android.os.Build

object Constants {
    const val BASE_URL = "https://fakestoreapi.com/"
    val CAMERA_PERMISSION = arrayOf(Manifest.permission.CAMERA)
    val READ_WRITE_EXTERNAL_PERMISSION = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VIDEO,
                Manifest.permission.READ_MEDIA_AUDIO
            )
        } else {
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
    val LOCATION_PERMISSION = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

}