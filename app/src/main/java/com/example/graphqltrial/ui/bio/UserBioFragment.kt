package com.example.graphqltrial.ui.bio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.api.Response
import com.example.graphqltrial.GetBioQuery
import com.example.graphqltrial.R
import com.example.graphqltrial.databinding.FragmentUserBioBinding
import com.example.graphqltrial.ui.GitHubViewModel
import com.example.graphqltrial.utils.Result
import com.example.graphqltrial.utils.loadImage
import com.example.graphqltrial.utils.showIf
import com.example.graphqltrial.utils.showIfNot
import com.example.graphqltrial.utils.showIfNotNull
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class UserBioFragment : Fragment() {

    private var _binding: FragmentUserBioBinding? = null
    private val binding get() = _binding!!

    private val gitHubViewModel by viewModels<GitHubViewModel>()

    private val repositoriesAdapter by lazy { UserBioRepositoriesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserBioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gitHubViewModel.getBio()
        observeBio()
    }

    private fun observeBio() {
        gitHubViewModel.bioData.observe(viewLifecycleOwner) { response ->
            binding.apply {
                progressBar.showIf(response is Result.Loading)
                linearLayoutUserDetail.showIfNot(response is Result.Loading)
                recyclerViewUserRepositories.showIf(response is Result.Success)
                texViewUserRepositoriesTitle.showIf(response is Result.Success)
            }
            when (response) {
                is Result.Success -> initUi(response)
                is Result.Error -> initErrorUi(response.message)
            }
        }
    }

    private fun initUi(result: Result<Response<GetBioQuery.Data>>) {
        val bio = result.value?.data?.viewer

        bio?.let {
            with(binding) {
                imageViewUserAvatar.loadImage(it.avatarUrl.toString(), requireContext())
                texViewUserName.showIfNotNull(it.name)
                texViewUserNickName.text = it.login
                texViewUserBio.showIfNotNull(it.bio)
                texViewUserFollowersCount.text =
                    getString(R.string.desc_bio_followers, it.followers.totalCount)
                texViewUserFollowingCount.text =
                    getString(R.string.desc_bio_following, it.following.totalCount)
                texViewUserEmail.text = it.email
                texViewUserWebsite.showIfNotNull(it.websiteUrl.toString())
                texViewUserTwitter.showIfNotNull(it.twitterUsername)
            }
            initRecyclerView(bio?.topRepositories?.nodes)
        }
    }

    private fun initRecyclerView(repositories: List<GetBioQuery.Node?>?) {
        binding.recyclerViewUserRepositories.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = repositoriesAdapter
        }
        repositoriesAdapter.updateItems(repositories)
    }

    private fun initErrorUi(message: String?) {
        binding.texViewError.showIfNotNull(message)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}