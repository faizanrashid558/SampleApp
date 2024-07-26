package com.example.sampleapp.presentation.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.sampleapp.R
import com.example.sampleapp.data.utils.Logger
import com.example.sampleapp.databinding.ActivityMainBinding
import com.example.sampleapp.presentation.viewmodel.MainViewModel
import com.example.sampleapp.presentation.viewmodel.ShareVM
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var vm: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initClicks()
        vm.getPost()
        Log.d("PRO_DETAIL","Experimental")
    }

    private fun initViews() {
        binding.apply {
            vm = ViewModelProvider(this@MainActivity)[MainViewModel::class.java]

            vm.productLiveData.observe(this@MainActivity) {
                Log.d("PRO_DETAIL",it.toString())
            }
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_container) as NavHostFragment
            navController = navHostFragment.navController
            load1.setOnClickListener {
                navController.navigate(R.id.fragmentOne)
            }
            load2.setOnClickListener {
                navController.navigate(R.id.fragmentTwo)
            }
            load3.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("name", "Faizan")
                bundle.putString("pass", "faizan@123")
                navController.navigate(R.id.fragmentThree, bundle)
            }
        }
    }

    private fun initClicks() {
        binding.apply {

        }
    }
}