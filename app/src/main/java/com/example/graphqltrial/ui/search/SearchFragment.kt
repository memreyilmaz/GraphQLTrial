package com.example.graphqltrial.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.graphqltrial.utils.Result
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.graphqltrial.R
import com.example.graphqltrial.databinding.FragmentSearchBinding
import com.example.graphqltrial.type.SearchType
import com.example.graphqltrial.ui.GitHubViewModel
import com.example.graphqltrial.utils.SearchSelection
import com.example.graphqltrial.utils.hideKeyboard
import com.example.graphqltrial.utils.show
import com.example.graphqltrial.utils.showIf
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import androidx.core.view.isVisible

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val gitHubViewModel by viewModels<GitHubViewModel>()

    private var firstSearchParam: String? = null
    private var secondSearchParam: String? = null

    private val args by navArgs<SearchFragmentArgs>()
    private var searchSelection: SearchSelection? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchSelection = args.searchSelection
        initSelectedUi()
    }

    private fun initSelectedUi() {
        when (searchSelection) {
            SearchSelection.User -> {
                setTitle(getString(R.string.button_desc_search_user))
                initSearchUserUi()
            }
            SearchSelection.Repository -> {
                setTitle(getString(R.string.button_desc_search_repo))
                initSearchRepositoryUi()
            }
            SearchSelection.Topic -> {
                setTitle(getString(R.string.button_desc_search_topic))
                initSearchTopicUi()
            }
            SearchSelection.General -> {
                setTitle(getString(R.string.button_desc_search))
                initSearchUi()
            }
        }
    }

    private fun observeData() {
        gitHubViewModel.userData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Result.Success -> {
                    //todo show result
                    clearForm()
                }
                is Result.Error -> {
                    //todo handle
                }
            }
            with(binding) {
                progressBar.showIf(response is Result.Loading)
            }
        }

        gitHubViewModel.repositoryData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Result.Success -> {
                    //todo show result
                    clearForm()
                }
                is Result.Error -> {
                    //todo handle
                }
            }
            with(binding) {
                progressBar.showIf(response is Result.Loading)
            }
        }

        gitHubViewModel.topicData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Result.Success -> {
                    //todo show result
                    clearForm()
                }
                is Result.Error -> {
                    //todo handle
                }
            }
            with(binding) {
                progressBar.showIf(response is Result.Loading)
            }
        }

        gitHubViewModel.searchResultsData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Result.Success -> {
                    //todo show result
                    clearForm()
                }
                is Result.Error -> {
                    //todo handle
                }
            }
            with(binding) {
                progressBar.showIf(response is Result.Loading)
            }
        }
    }

    private fun initSearchUserUi() {
        with(binding) {
            buttonSearch.apply {
                text = getString(R.string.button_desc_search_user)
                setOnClickListener {
                    hideKeyboard()
                    gitHubViewModel.getUser(firstSearchParam!!)
                }
            }
            textInputLayoutFirst.apply {
                hint = getString(R.string.edit_text_desc_enter_user_name)
                show()

            }
            textInputEditTextFirst.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    firstSearchParam = s.toString().trim()
                    binding.textInputLayoutFirst.error =
                        if (firstSearchParam!!.length > GITHUB_USER_NAME_MAX_LENGTH) getString(R.string.desc_error_long_username) else null

                    binding.buttonSearch.isEnabled =
                        !firstSearchParam.isNullOrEmpty() && firstSearchParam!!.length <= GITHUB_USER_NAME_MAX_LENGTH

                }

                override fun afterTextChanged(s: Editable) {}
            })
        }
        observeData()
    }

    private fun initSearchTopicUi() {
        with(binding) {
            buttonSearch.apply {
                text = getString(R.string.button_desc_search_topic)
                setOnClickListener {
                    hideKeyboard()
                    gitHubViewModel.getTopic(firstSearchParam!!)
                }
            }
            textInputLayoutFirst.apply {
                hint = getString(R.string.edit_text_desc_enter_topic)
                show()
            }

            textInputEditTextFirst.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    firstSearchParam = s.toString().trim()
                    binding.buttonSearch.isEnabled = !firstSearchParam.isNullOrEmpty()
                }

                override fun afterTextChanged(s: Editable) {}
            })
        }
        observeData()
    }

    private fun initSearchRepositoryUi() {
        with(binding) {
            buttonSearch.apply {
                text = getString(R.string.button_desc_search_repo)
                setOnClickListener {
                    hideKeyboard()
                    gitHubViewModel.getRepository(firstSearchParam!!, secondSearchParam!!)
                }
            }
            textInputLayoutFirst.apply {
                hint = getString(R.string.edit_text_desc_enter_repository_name)
                show()
            }
            textInputLayoutSecond.apply {
                hint = getString(R.string.edit_text_desc_enter_user_name)
                show()
            }
        }

        initSearchRepositoryFormValidation()
        observeData()
    }

    private fun initSearchUi() {
        with(binding) {
            texViewTitleSearchType.show()
            radioGroupSearchType.show()
            buttonSearch.apply {
                text = getString(R.string.button_desc_search)
                setOnClickListener {
                    hideKeyboard()
                    if (binding.radioButtonSearchRepository.isChecked) {
                        gitHubViewModel.search(firstSearchParam!!, SearchType.REPOSITORY)
                    } else {
                        gitHubViewModel.search(firstSearchParam!!, SearchType.USER)
                    }
                }
            }
            textInputLayoutFirst.apply {
                hint = getString(R.string.button_desc_search_repo)
                show()
            }

            textInputEditTextFirst.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    firstSearchParam = s.toString().trim()
                    if (binding.radioButtonSearchRepository.isChecked) {
                        binding.textInputLayoutFirst.error =
                            if (firstSearchParam!!.length > GITHUB_REPOSITORY_NAME_MAX_LENGTH) getString(
                                R.string.desc_error_long_repo_name
                            ) else null
                        binding.buttonSearch.isEnabled =
                            !firstSearchParam.isNullOrEmpty() && firstSearchParam!!.length <= GITHUB_REPOSITORY_NAME_MAX_LENGTH
                    } else {
                        binding.textInputLayoutFirst.error =
                            if (firstSearchParam!!.length > GITHUB_USER_NAME_MAX_LENGTH) getString(R.string.desc_error_long_username) else null
                        binding.buttonSearch.isEnabled =
                            !firstSearchParam.isNullOrEmpty() && firstSearchParam!!.length <= GITHUB_USER_NAME_MAX_LENGTH
                    }
                }

                override fun afterTextChanged(s: Editable) {}
            })

            radioGroupSearchType.setOnCheckedChangeListener { _, id ->
                when (id) {
                    R.id.radioButton_search_repository -> {
                        textInputLayoutFirst.hint = getString(R.string.button_desc_search_repo)
                    }
                    R.id.radioButton_search_user -> {
                        textInputLayoutFirst.hint = getString(R.string.button_desc_search_user)
                        if (textInputEditTextFirst.text.toString()
                                .trim().length >= GITHUB_USER_NAME_MAX_LENGTH
                        ) {
                            buttonSearch.isEnabled = false
                        }
                    }
                }
            }

            observeData()
        }
    }

    private fun initSearchRepositoryFormValidation() {
        val addNewFormTextWatcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                firstSearchParam = binding.textInputEditTextFirst.text.toString().trim()
                secondSearchParam = binding.textInputEditTextSecond.text.toString().trim()

                binding.textInputLayoutFirst.error =
                    if (firstSearchParam!!.length > GITHUB_REPOSITORY_NAME_MAX_LENGTH) getString(R.string.desc_error_long_repo_name) else null

                binding.textInputLayoutSecond.error =
                    if (secondSearchParam!!.length > GITHUB_USER_NAME_MAX_LENGTH) getString(R.string.desc_error_long_username) else null

                binding.buttonSearch.isEnabled =
                    !firstSearchParam.isNullOrEmpty() && firstSearchParam!!.length <= GITHUB_REPOSITORY_NAME_MAX_LENGTH && !secondSearchParam.isNullOrEmpty() && secondSearchParam!!.length <= GITHUB_USER_NAME_MAX_LENGTH
            }

            override fun afterTextChanged(s: Editable) {}
        }

        with(binding) {
            textInputEditTextFirst.addTextChangedListener(addNewFormTextWatcher)
            textInputEditTextSecond.addTextChangedListener(addNewFormTextWatcher)
        }
    }

    private fun clearForm() {
        with(binding) {
            textInputEditTextFirst.text?.clear()
            if (textInputLayoutSecond.isVisible) {
                textInputEditTextSecond.text?.clear()
            }
            if (radioGroupSearchType.isVisible) {
                radioButtonSearchRepository.isChecked = true
            }
        }
    }

    private fun hideKeyboard() {
        view?.let {
            activity?.hideKeyboard(it)
        }
    }

    private fun setTitle(title: String) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = title
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val GITHUB_USER_NAME_MAX_LENGTH = 39
        const val GITHUB_REPOSITORY_NAME_MAX_LENGTH = 100
    }
}