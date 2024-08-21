package com.example.sampleapp.presentation.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.sampleapp.R
import com.example.sampleapp.databinding.ActivityNewBinding
import com.example.sampleapp.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewActivity : AppCompatActivity() {
    lateinit var binding : ActivityNewBinding
    private lateinit var navController: NavController
    private lateinit var vm: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityNewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
//        vm.getPost()

    }

    private fun initViews() {
        binding.apply {
            vm = ViewModelProvider(this@NewActivity)[MainViewModel::class.java]
//            vm.productLiveData.observe(this@NewActivity) {
//            }
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_container) as NavHostFragment
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
                navController.navigate(R.id.fragmentThree)
            }
        }
    }
}