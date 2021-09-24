package com.example.graphqltrial.di

import com.example.graphqltrial.data.repo.GitHubRepository
import com.example.graphqltrial.data.repo.GitHubRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule {

    @Binds
    @ViewModelScoped
    abstract fun bindRepository(repository: GitHubRepositoryImpl): GitHubRepository
}