package com.example.graphqltrial.data.repo

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.example.graphqltrial.GithubUserQuery
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor(private val client: ApolloClient) :
    GitHubRepository {
    override suspend fun getUser(login: String): Response<GithubUserQuery.Data> {
        return client.query(GithubUserQuery(login = login)).await()
    }
}