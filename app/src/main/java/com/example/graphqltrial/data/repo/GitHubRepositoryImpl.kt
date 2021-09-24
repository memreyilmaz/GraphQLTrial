package com.example.graphqltrial.data.repo

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.example.graphqltrial.GetBioQuery
import com.example.graphqltrial.GetRepositoryQuery
import com.example.graphqltrial.GetTopicQuery
import com.example.graphqltrial.GithubUserQuery
import com.example.graphqltrial.SearchQuery
import com.example.graphqltrial.type.OrderDirection
import com.example.graphqltrial.type.RepositoryOrder
import com.example.graphqltrial.type.RepositoryOrderField
import com.example.graphqltrial.type.SearchType
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor(private val client: ApolloClient) :
    GitHubRepository {
    override suspend fun getUser(login: String): Response<GithubUserQuery.Data> {
        return client.query(GithubUserQuery(login = login)).await()
    }

    override suspend fun getBio(): Response<GetBioQuery.Data> {
        return client.query(
            GetBioQuery(
                first = Input.fromNullable(BIO_REPOSITORY_DEFAULT_COUNT),
                orderBy = RepositoryOrder(
                    field_ = RepositoryOrderField.STARGAZERS,
                    direction = OrderDirection.ASC
                )
            )
        ).await()
    }

    override suspend fun getRepository(
        name: String,
        owner: String
    ): Response<GetRepositoryQuery.Data> {
        return client.query(GetRepositoryQuery(name = name, owner = owner)).await()
    }

    override suspend fun getTopic(name: String, first: Int): Response<GetTopicQuery.Data> {
        return client.query(GetTopicQuery(name = name, first = Input.fromNullable(first))).await()
    }

    override suspend fun search(
        query: String,
        type: SearchType,
        first: Int
    ): Response<SearchQuery.Data> {
        return client.query(
            SearchQuery(
                query = query,
                type = type,
                first = Input.fromNullable(first)
            )
        ).await()
    }

    companion object {
        const val BIO_REPOSITORY_DEFAULT_COUNT = 10
    }
}