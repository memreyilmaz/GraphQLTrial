<h1 align="center">GraphQLTrial</h1>

<p align="center">
GraphQLTrial is a demo application based on modern Android application tech-stacks and MVVM architecture.<br>App fetching data from the network via repository pattern.<br>
</p>
</br>
  ### Please add your GitHub Token to GITHUB_TOKEN field in Constants to build and run this project

## Tech stack & Open-source libraries
- Minimum SDK level 26
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines)  for asynchronous.
- [Hilt](https://dagger.dev/hilt/) for dependency injection.
- JetPack
  - LiveData - notify domain layer data to views.
  - Lifecycle - dispose of observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
  - Navigation - Navigation refers to the interactions that allow users to navigate across, into, and back out from the different pieces of content within your app.
- Architecture
  - MVVM Architecture (View - ViewBinding - ViewModel - Model)
  - Repository pattern
- [Apollo](https://www.apollographql.com/) - The industry-standard open-source GraphQL client for web, iOS and Android apps with everything you need to fetch, cache, and modify application data.
- [Glide](https://github.com/bumptech/glide) - Glide is a fast and efficient open source media management and image loading framework for Android that wraps media decoding, memory and disk caching, and resource pooling into a simple and easy to use interface.
- Custom Views
  - [CircleImageView](https://github.com/hdodenhof/CircleImageView) - A fast circular ImageView perfect for profile images.

## Architecture
GraphQLTrial is based on MVVM architecture and a repository pattern.

## GraphQL Endpoint

GraphQLTrial is using the [GitHub GraphQL API](https://docs.github.com/en/graphql/overview/about-the-graphql-api) endpoint for data source.<br>
GitHub GraphQL API provides precise and flexible queries for the data needed to integrate with GitHub.

