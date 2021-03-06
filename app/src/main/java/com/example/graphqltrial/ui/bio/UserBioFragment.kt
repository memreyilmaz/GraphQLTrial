package com.example.graphqltrial.ui.bio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.graphqltrial.R
import com.example.graphqltrial.databinding.FragmentUserBioBinding
import com.example.graphqltrial.ui.GitHubViewModel
import com.example.graphqltrial.data.model.Repository
import com.example.graphqltrial.utils.Result
import com.example.graphqltrial.data.model.User
import com.example.graphqltrial.utils.loadImage
import com.example.graphqltrial.utils.showIf
import com.example.graphqltrial.utils.showIfNotNull
import com.example.graphqltrial.data.mapper.toUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class UserBioFragment : Fragment() {

    private var _binding: FragmentUserBioBinding? = null
    private val binding get() = _binding!!

    private val gitHubViewModel by viewModels<GitHubViewModel>()

    private val repositoryListAdapter by lazy { RepositoryListAdapter() }

    private val args by navArgs<UserBioFragmentArgs>()
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_userBioFragment_to_mainFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user = args.user
        if (user != null) {
            initUi(user)
            setTitle(user?.nickname)
        } else {
            gitHubViewModel.getBio()
            observeBio()
        }
    }

    private fun observeBio() {
        gitHubViewModel.bioData.observe(viewLifecycleOwner) { response ->
            binding.apply {
                progressBar.showIf(response is Result.Loading)
                linearLayoutUserDetail.showIf(response is Result.Success)
                recyclerViewUserRepositories.showIf(response is Result.Success)
                texViewUserRepositoriesTitle.showIf(response is Result.Success)
            }
            when (response) {
                is Result.Success -> {
                    val user = response.value?.data?.viewer?.toUser()
                    setTitle(user?.nickname)
                    initUi(user)
                }
                is Result.Error -> initErrorUi(response.message)
            }
        }
    }

    private fun initUi(user: User?) {
        if (user == null) return

        with(binding) {
            imageViewUserAvatar.loadImage(user.avatar, requireContext())
            texViewUserName.showIfNotNull(user.name)
            texViewUserNickName.text = user.nickname
            texViewUserBio.showIfNotNull(user.bio)
            texViewUserFollowersCount.text =
                getString(R.string.desc_bio_followers, user.followers)
            texViewUserFollowingCount.text =
                getString(R.string.desc_bio_following, user.following)
            texViewUserEmail.text = user.email
            texViewUserWebsite.showIfNotNull(user.website)
            texViewUserTwitter.showIfNotNull(user.twitterUser)
        }
        initRecyclerView(user.repositories)
    }

    private fun initRecyclerView(repositories: List<Repository>) {
        binding.recyclerViewUserRepositories.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = repositoryListAdapter
        }
        repositoryListAdapter.updateItems(repositories)
    }

    private fun initErrorUi(message: String?) {
        binding.texViewError.showIfNotNull(message)
    }

    private fun setTitle(title: String?) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = title
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            requireActivity().onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}