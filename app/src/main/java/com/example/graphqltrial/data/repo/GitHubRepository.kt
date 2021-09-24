package com.example.graphqltrial.data.repo

import com.apollographql.apollo.api.Response
import com.example.graphqltrial.GithubUserQuery

interface GitHubRepository {

    suspend fun getUser(login: String) : Response<GithubUserQuery.Data>

}