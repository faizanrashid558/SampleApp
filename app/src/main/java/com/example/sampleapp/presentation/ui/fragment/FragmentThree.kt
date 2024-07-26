package com.example.sampleapp.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.sampleapp.databinding.FragmentThreeBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class FragmentThree : Fragment() {
    lateinit var binding : FragmentThreeBinding
    private var name : String?=null
    private var pass : String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString("name")
            pass = it.getString("pass")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentThreeBinding.inflate(layoutInflater)
        initViews()
        initClicks()
        return binding.root
    }

    private fun initViews() {
        binding.apply {
            //cold  flow
//            val intFlow = flow {
//                repeat(60) {
//                    emit(it+1)
//                    emittedNum.text  ="${it+1}"
//                    delay(1000)
//                }
//            }
//
//            lifecycleScope.launch {
//                intFlow.collect{
//                    delay(2000)
//                    collectedNum.text = "$it"
//                }
//            }
            // hot flow
            val intFlow = MutableStateFlow(0)
            lifecycleScope.launch {
                repeat(60) {
                    intFlow.emit(it + 1)
                    emittedNum.text = "${it + 1}"
                    delay(1000)
                }
            }

            lifecycleScope.launch {
                intFlow.map {
                    it*it
                }.filter {
                    it%2==0
                }.collect {
                    collectedNum.text = "$it"
                    delay(2000)
                }
            }
        }
    }


    private fun initClicks() {
        binding.apply {


        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentThree().apply {
                arguments = Bundle().apply {

                }
            }
    }
}