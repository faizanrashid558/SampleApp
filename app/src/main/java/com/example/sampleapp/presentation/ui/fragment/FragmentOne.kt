package com.example.sampleapp.presentation.ui.fragment

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.example.sampleapp.data.constants.Constants.LOCATION_PERMISSION
import com.example.sampleapp.data.constants.Constants.READ_WRITE_EXTERNAL_PERMISSION
import com.example.sampleapp.data.local.AppPreferences
import com.example.sampleapp.data.mapper.checkPerLoc
import com.example.sampleapp.data.mapper.checkPerReadWrite
import com.example.sampleapp.data.mapper.reqPerAll
import com.example.sampleapp.data.mapper.toast
import com.example.sampleapp.data.qualifier.UserQualifier
import com.example.sampleapp.databinding.FragmentOneBinding
import com.example.sampleapp.domain.interfaces.Userinterface
import com.example.sampleapp.presentation.viewmodel.ShareVM
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentOne : BaseFragment() {
    @Inject
    @UserQualifier
    lateinit var userinterface: Userinterface
    private lateinit var binding: FragmentOneBinding
    private val sharedViewModel: ShareVM by activityViewModels()
    private var fromSetting = false

    @Inject
    lateinit var appPref: AppPreferences

    private val perLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions: Map<String, Boolean> ->
            val allGranted = permissions.all { it.value }
            if (allGranted) {
                requireContext().toast("All permissions granted")
                // Do your work
            } else {
                appPref.perStorage++
                requireContext().toast("All permissions Denied")
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOneBinding.inflate(layoutInflater)
        // For Location
        if (!requireContext().checkPerReadWrite()) {
            requireActivity().reqPerAll(READ_WRITE_EXTERNAL_PERMISSION,appPref.perStorage) {
                when (it) {
                    "granted" -> {
                        requireContext().toast("Granted")
                        //Do your work
                    }

                    "launcher" -> {
                        perLauncher.launch(READ_WRITE_EXTERNAL_PERMISSION)
                    }

                    "setting_ok" -> {
                        fromSetting = true
                    }

                    "setting_cancel" -> {
                        fromSetting = false
                    }
                }
            }
        }

        return binding.root
    }



    override fun onResume() {
        super.onResume()
        Log.d("lifecycleState", "onResume:")
        if (fromSetting) {
            fromSetting = false
            // For Storage
            if (requireContext().checkPerReadWrite()) {
                requireContext().toast("Granted")
                //Do your work
            } else {
                requireContext().toast("Denied")
            }

        }
    }

}