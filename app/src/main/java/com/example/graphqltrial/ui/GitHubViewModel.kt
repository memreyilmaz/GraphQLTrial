package com.example.graphqltrial.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.graphqltrial.GithubUserQuery
import com.example.graphqltrial.data.repo.GitHubRepository
import com.example.graphqltrial.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class GitHubViewModel @Inject constructor(
    private val repository: GitHubRepository,
) : ViewModel() {

    private val _userData by lazy { MutableLiveData<Result<Response<GithubUserQuery.Data>>>() }
    val userData: LiveData<Result<Response<GithubUserQuery.Data>>>
        get() = _userData

    fun getUser(login: String) = viewModelScope.launch {
        _userData.postValue(Result.Loading())
        try {
            val response = repository.getUser(login)
            _userData.postValue(Result.Success(response))
        } catch (e: ApolloException) {
            _userData.postValue(Result.Error("Error fetching user"))
        }
    }
}