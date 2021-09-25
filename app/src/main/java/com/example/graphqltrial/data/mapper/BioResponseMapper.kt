package com.example.graphqltrial.data.mapper

import com.example.graphqltrial.GetBioQuery
import com.example.graphqltrial.GithubUserQuery
import com.example.graphqltrial.data.model.Repository
import com.example.graphqltrial.data.model.User

fun GetBioQuery.Viewer.toUser() = User(
    avatar = avatarUrl.toString(),
    name = name,
    nickname = login,
    bio = bio,
    followers = followers.totalCount.toString(),
    following = following.totalCount.toString(),
    email = email,
    website = websiteUrl.toString(),
    twitterUser = twitterUsername.toString(),
    repositories = createRepositoryListFromBio(topRepositories.nodes)
)

fun GithubUserQuery.User.toUser() = User(
    avatar = avatarUrl.toString(),
    name = name,
    nickname = login,
    bio = bio,
    followers = followers.totalCount.toString(),
    following = following.totalCount.toString(),
    email = email,
    website = websiteUrl.toString(),
    twitterUser = twitterUsername.toString(),
    repositories = createRepositoryList(topRepositories.nodes)
)

private fun createRepositoryListFromBio(nodes: List<GetBioQuery.Node?>?): List<Repository> {
    val list: MutableList<Repository> = mutableListOf()

    nodes?.forEach {

        val repository = Repository(
            name = it?.name.toString(),
            description = it?.description,
            url = it?.url.toString(),
            stargazerCount = it?.stargazerCount.toString(),
            creationDate = it?.createdAt.toString()
        )
        list.add(repository)
    }
    return list
}

private fun createRepositoryList(nodes: List<GithubUserQuery.Node?>?): List<Repository> {
    val list: MutableList<Repository> = mutableListOf()

    nodes?.forEach {

        val repository = Repository(
            name = it?.name.toString(),
            description = it?.description,
            url = it?.url.toString(),
            stargazerCount = it?.stargazerCount.toString(),
            creationDate = it?.createdAt.toString()
        )
        list.add(repository)
    }
    return list
}