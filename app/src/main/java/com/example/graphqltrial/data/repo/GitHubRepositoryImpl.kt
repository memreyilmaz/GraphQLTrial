package com.example.graphqltrial.data.repo

import com.apollographql.apollo.ApolloClient
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor(private val client: ApolloClient) :
    GitHubRepository {}