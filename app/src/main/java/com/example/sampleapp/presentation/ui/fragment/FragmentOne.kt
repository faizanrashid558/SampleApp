package com.example.sampleapp.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.sampleapp.data.qualifier.ClientQualifier
import com.example.sampleapp.data.qualifier.UserQualifier
import com.example.sampleapp.databinding.FragmentOneBinding
import com.example.sampleapp.domain.interfaces.Userinterface
import com.example.sampleapp.presentation.viewmodel.ShareVM
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentOne : Fragment() {

//    @Inject
//    lateinit var userRepository: UserRepository

    // We can use named Qualifier or Custom qualifier
    @Inject
//    @Named("client")
    @UserQualifier
    lateinit var userinterface: Userinterface
    private lateinit var binding : FragmentOneBinding
    private val sharedViewModel : ShareVM by activityViewModels()
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("FRAGMIM", "onCreate: 1")
        outState.putString("FRAGMENT","10")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d("FRAGMIM", "onCreate: 2")
        savedInstanceState?.let {
            val frag = it.getInt("FRAGMENT")
            Log.d("FRAGMIM", "onCreate: $frag")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOneBinding.inflate(layoutInflater)
//        userRepository.saveUser("","")
        userinterface.saveUser("Faizan","password")
        binding.incBy1.setOnClickListener {
            sharedViewModel.updateVal()
        }
        return binding.root
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            FragmentOne().apply {
                arguments = Bundle().apply {
                }
            }
    }
}