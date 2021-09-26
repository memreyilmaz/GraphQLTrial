package com.example.graphqltrial.data.mapper

import com.example.graphqltrial.GetBioQuery
import com.example.graphqltrial.GithubUserQuery
import com.example.graphqltrial.SearchQuery
import com.example.graphqltrial.data.model.Repository
import com.example.graphqltrial.data.model.User

/*
 * Maps User Bio response to User data class
 */
fun GetBioQuery.Viewer.toUser() = User(
    avatar = avatarUrl.toString(),
    name = name,
    nickname = login,
    bio = bio,
    followers = followers.totalCount.toString(),
    following = following.totalCount.toString(),
    email = email,
    website = websiteUrl?.toString(),
    twitterUser = twitterUsername.let { it.toString() },
    repositories = createRepositoryListFromBio(topRepositories.nodes)
)

/*
 * Maps User Search response to User data class
 */
fun GithubUserQuery.User.toUser() = User(
    avatar = avatarUrl.toString(),
    name = name,
    nickname = login,
    bio = bio,
    followers = followers.totalCount.toString(),
    following = following.totalCount.toString(),
    email = email,
    website = websiteUrl?.toString(),
    twitterUser = twitterUsername.let { it.toString() },
    repositories = createRepositoryList(topRepositories.nodes)
)

/*
 * Maps Repository Search list response to list of Repository data class
 */
fun List<SearchQuery.Node?>.toRepositoryList() :List<Repository> {
    val repositoryList = this.filterNot { it?.asRepository == null }
    val list: MutableList<Repository> = mutableListOf()

    repositoryList.forEach {
        val repository = Repository(
            name = it?.asRepository?.repositoryName.toString(),
            description = it?.asRepository?.description,
            url = it?.asRepository?.url.toString(),
            stargazerCount = it?.asRepository?.stargazerCount.toString(),
            creationDate = it?.asRepository?.createdAt.toString()
        )
        list.add(repository)
    }
    return list
}

/*
 * Maps User Search list response to list of User data class
 */
fun List<SearchQuery.Node?>.toUserList() :List<User> {
    val userList = this.filterNot { it?.asUser == null }
    val list: MutableList<User> = mutableListOf()

    userList.forEach {
        val user = User(
            avatar = it?.asUser?.avatarUrl.toString(),
            name = it?.asUser?.userName,
            nickname = it?.asUser?.login,
            bio = null,
            followers = it?.asUser?.followers?.totalCount.toString(),
            following = it?.asUser?.following?.totalCount.toString(),
            email = it?.asUser?.email,
            website = null,
            twitterUser = null,
            repositories = emptyList()
        )
        list.add(user)
    }
    return list
}

/*
 * Maps User Bio response's repository list to list of Repository data class
 */
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

/*
 * Maps User Search responses repository list to list of Repository data class
 */
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