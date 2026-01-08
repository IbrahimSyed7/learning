package com.example.learning1.di.module

import com.example.learning1.di.APIService
import com.example.learning1.utils.CoroutineDispatcherProvider
import com.example.learning1.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private val baseUrl = "https://dummyjson.com/"

    @Provides
    @Singleton
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory, okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder().client(okHttpClient).addConverterFactory(gsonConverterFactory).baseUrl(baseUrl).build()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory() : GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideHttpClient() : OkHttpClient {
     return OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor()).build()
    }

    @Provides
    @Singleton
    fun provideAPIService(retrofit: Retrofit) : APIService{
        return retrofit.create(APIService::class.java)
    }

    @Provides
    @Singleton
    fun provideDispatchers(): DispatcherProvider = CoroutineDispatcherProvider()
}