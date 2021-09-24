package com.example.graphqltrial.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.graphqltrial.GetBioQuery
import com.example.graphqltrial.GetRepositoryQuery
import com.example.graphqltrial.GetTopicQuery
import com.example.graphqltrial.GithubUserQuery
import com.example.graphqltrial.SearchQuery
import com.example.graphqltrial.data.repo.GitHubRepository
import com.example.graphqltrial.type.SearchType
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

    private val _bioData by lazy { MutableLiveData<Result<Response<GetBioQuery.Data>>>() }
    val bioData: LiveData<Result<Response<GetBioQuery.Data>>>
        get() = _bioData

    private val _repositoryData by lazy { MutableLiveData<Result<Response<GetRepositoryQuery.Data>>>() }
    val repositoryData: LiveData<Result<Response<GetRepositoryQuery.Data>>>
        get() = _repositoryData

    private val _topicData by lazy { MutableLiveData<Result<Response<GetTopicQuery.Data>>>() }
    val topicData: LiveData<Result<Response<GetTopicQuery.Data>>>
        get() = _topicData

    private val _searchResultsData by lazy { MutableLiveData<Result<Response<SearchQuery.Data>>>() }
    val searchResultsData: LiveData<Result<Response<SearchQuery.Data>>>
        get() = _searchResultsData

    fun getUser(login: String) = viewModelScope.launch {
        _userData.postValue(Result.Loading())
        try {
            _userData.postValue(Result.Success(repository.getUser(login)))
        } catch (e: ApolloException) {
            _userData.postValue(Result.Error("Error fetching user"))
        }
    }

    fun getBio() = viewModelScope.launch {
        _bioData.postValue(Result.Loading())
        try {
            _bioData.postValue(Result.Success(repository.getBio()))
        } catch (e: ApolloException) {
            _bioData.postValue(Result.Error("Error fetching user bio"))
        }
    }

    fun getRepository(name: String, owner: String) = viewModelScope.launch {
        _repositoryData.postValue(Result.Loading())
        try {
            _repositoryData.postValue(Result.Success(repository.getRepository(name, owner)))
        } catch (e: ApolloException) {
            _repositoryData.postValue(Result.Error("Error fetching repository"))
        }
    }

    fun getTopic(name: String) = viewModelScope.launch {
        _topicData.postValue(Result.Loading())
        try {
            _topicData.postValue(Result.Success(repository.getTopic(name)))
        } catch (e: ApolloException) {
            _topicData.postValue(Result.Error("Error fetching repository"))
        }
    }

    fun search(query: String, type: SearchType) = viewModelScope.launch {
        _searchResultsData.postValue(Result.Loading())
        try {
            _searchResultsData.postValue(Result.Success(repository.search(query, type)))
        } catch (e: ApolloException) {
            _searchResultsData.postValue(Result.Error("Error fetching search results"))
        }
    }
}