package com.example.sampleapp.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sampleapp.R
import com.example.sampleapp.data.utils.ApiState
import com.example.sampleapp.databinding.FragmentTwoBinding
import com.example.sampleapp.presentation.adapter.PostAdapter
import com.example.sampleapp.presentation.viewmodel.MainViewModel
import com.example.sampleapp.presentation.viewmodel.ShareVM
import kotlinx.coroutines.flow.collect


class FragmentTwo : Fragment() {
    private val sharedViewModel : ShareVM by activityViewModels()
    private val mainVm : MainViewModel by activityViewModels()
    private lateinit var binding: FragmentTwoBinding
    private lateinit var postAdapter: PostAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTwoBinding.inflate(layoutInflater)
        sharedViewModel.message.observe(requireActivity()){
            binding.incValue.text=it.toString()
        }
        initViews()
        return binding.root
    }

    private fun initViews() {
        binding.apply {
            postAdapter = PostAdapter()
            initRv()


            lifecycleScope.launchWhenStarted {
                mainVm.apiStateFlow.collect(){
                    when(it){
                        is ApiState.Success ->{
                            rv.isVisible = true
                            loading.isVisible=false
                            error.isVisible=false
                            postAdapter.submitList(it.data)
                        }
                        is ApiState.Failure ->{
                            rv.isVisible = false
                            loading.isVisible=false
                            error.isVisible=true
                            error.text = it.msg.toString()
                        }
                        is ApiState.Loading ->{
                            rv.isVisible = false
                            loading.isVisible=true
                            error.isVisible=false
                        }
                        is ApiState.EmptyState ->{

                        }
                    }
                }
            }
        }
    }

    private fun initRv() {
        binding.apply {
            rv.apply {
                hasFixedSize()
                layoutManager =  LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                adapter = postAdapter
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentTwo().apply {
                arguments = Bundle().apply {
                }
            }
    }
}