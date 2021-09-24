package com.example.graphqltrial.data.repo

import com.apollographql.apollo.api.Response
import com.example.graphqltrial.GetBioQuery
import com.example.graphqltrial.GetRepositoryQuery
import com.example.graphqltrial.GetTopicQuery
import com.example.graphqltrial.GithubUserQuery
import com.example.graphqltrial.SearchQuery
import com.example.graphqltrial.type.SearchType

interface GitHubRepository {

    suspend fun getUser(login: String) : Response<GithubUserQuery.Data>

    suspend fun getBio() : Response<GetBioQuery.Data>

    suspend fun getRepository(name:String, owner:String) : Response<GetRepositoryQuery.Data>

    suspend fun getTopic(name: String, first: Int) : Response<GetTopicQuery.Data>

    suspend fun search(query: String, type: SearchType, first: Int) : Response<SearchQuery.Data>

}