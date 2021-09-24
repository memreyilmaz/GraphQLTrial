package com.example.graphqltrial.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.graphqltrial.utils.Result
import com.example.graphqltrial.R
import com.example.graphqltrial.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val gitHubViewModel by viewModels<GitHubViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gitHubViewModel.getUser("jakewharton")

        gitHubViewModel.userData.observe(this) { response ->
            when (response) {
                is Result.Success -> {
                    binding.text.text = response.value?.data.toString()
                }
                is Result.Error -> {
                    binding.text.text = response.message.toString()
                }
            }

        }
    }
}