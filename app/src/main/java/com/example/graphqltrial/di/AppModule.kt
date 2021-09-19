package com.example.graphqltrial.di

import com.apollographql.apollo.ApolloClient
import com.example.graphqltrial.utils.BASE_URL
import com.example.graphqltrial.utils.GITHUB_TOKEN
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApolloClient() : ApolloClient {
        return ApolloClient.builder()
            .serverUrl(BASE_URL)
            .okHttpClient(OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $GITHUB_TOKEN")
                        .build()

                    chain.proceed(request)
                }.build()
            ).build()
    }
}
