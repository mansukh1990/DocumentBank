package com.example.documentbank.DocumentBank.data.di

import android.content.Context
import android.content.SharedPreferences
import com.example.documentbank.DocumentBank.data.network.ApiService
import com.example.documentbank.DocumentBank.data.repository.RepositoryImpl
import com.example.documentbank.DocumentBank.domain.repository.MainRepository
import com.example.documentbank.DocumentBank.utils.TokenManager
import com.example.documentbank.common.ApiConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(
        okHttpClient: OkHttpClient
    ): ApiService = Retrofit.Builder().run {
        baseUrl(ApiConstants.BASE_URL)
        client(okHttpClient)
        addConverterFactory(GsonConverterFactory.create())
        build()

    }.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideAppRepository(
        apiService: ApiService,
        sharedPreferences: TokenManager
    ): MainRepository {
        return RepositoryImpl(apiService, sharedPreferences)
    }

}