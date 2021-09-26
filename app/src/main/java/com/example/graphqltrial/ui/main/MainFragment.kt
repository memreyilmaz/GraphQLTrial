package com.example.graphqltrial.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.graphqltrial.databinding.FragmentMainBinding
import com.example.graphqltrial.utils.SearchSelection

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonListeners()
    }

    private fun setButtonListeners() {
        with(binding) {
            buttonShowBio.setOnClickListener {
                val action = MainFragmentDirections.actionMainFragmentToUserBioFragment(null)
                findNavController().navigate(action)
            }
            buttonSearchUser.setOnClickListener {
                val action =
                    MainFragmentDirections.actionMainFragmentToSearchFragment(SearchSelection.User)
                findNavController().navigate(action)
            }
            buttonSearchRepo.setOnClickListener {
                val action =
                    MainFragmentDirections.actionMainFragmentToSearchFragment(SearchSelection.Repository)
                findNavController().navigate(action)
            }
            buttonSearchTopic.setOnClickListener {
                val action =
                    MainFragmentDirections.actionMainFragmentToSearchFragment(SearchSelection.Topic)
                findNavController().navigate(action)
            }
            buttonSearch.setOnClickListener {
                val action =
                    MainFragmentDirections.actionMainFragmentToSearchFragment(SearchSelection.General)
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}