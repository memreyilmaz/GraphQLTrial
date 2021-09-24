package com.example.graphqltrial.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.graphqltrial.R
import com.example.graphqltrial.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonListeners()
    }

    private fun setButtonListeners() {
        with(binding){
            buttonShowBio.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_userBioFragment)
            }
            buttonSearchUser.setOnClickListener {
                //TODO navigate to Search
            }
            buttonSearchRepo.setOnClickListener {
                //TODO navigate to Search
            }
            buttonSearchTopic.setOnClickListener {
                //TODO navigate to Search
            }
            buttonSearch.setOnClickListener {
                //TODO navigate to Search
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}