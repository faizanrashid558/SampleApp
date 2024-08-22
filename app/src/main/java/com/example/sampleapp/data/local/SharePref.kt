package com.example.sampleapp.data.local

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("App_Preference", Context.MODE_PRIVATE)
    private val edit = prefs.edit()

    var perCamera : Int
        get() { return prefs.getInt("perCamera",0)}
        set(value) {
            edit.putInt("perCamera",value).apply()
        }
    var perStorage : Int
        get() { return prefs.getInt("perStorage",0)}
        set(value) {
            edit.putInt("perStorage",value).apply()
        }
    var perLoc : Int
        get() { return prefs.getInt("perLoc",0)}
        set(value) {
            edit.putInt("perLoc",value).apply()
        }
}