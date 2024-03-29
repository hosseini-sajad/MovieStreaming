package com.moviestreaming.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.moviestreaming.BuildConfig
import com.moviestreaming.utils.Constants.API_KEY
import com.moviestreaming.utils.Constants.API_KEY_STRING
import com.moviestreaming.utils.Constants.BASE_URL
import com.moviestreaming.utils.Constants.NETWORK_TIMEOUT
import com.moviestreaming.data.source.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideBaseUrl() = BASE_URL

    @Provides
    @Singleton
    fun provideConnectionTimeOut() = NETWORK_TIMEOUT

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideOkHttpClient(networkTimeout: Long): OkHttpClient {
        val headerInterceptor = Interceptor { chain ->
            val original: Request = chain.request()
            val originalHttpUrl: HttpUrl = original.url

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter(API_KEY_STRING, API_KEY)
                .build()

            // Request customization: add request headers

            // Request customization: add request headers
            val requestBuilder: Request.Builder = original.newBuilder()
                .url(url)

            val request: Request = requestBuilder.build()

            return@Interceptor chain.proceed(request)
        }

        val httpClient = OkHttpClient.Builder()

        return if (BuildConfig.DEBUG) {
            val loginInterceptor = HttpLoggingInterceptor()
            loginInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
            loginInterceptor.level = HttpLoggingInterceptor.Level.BODY

            httpClient.addInterceptor(loginInterceptor)
                .addInterceptor(headerInterceptor)
                .readTimeout(networkTimeout, TimeUnit.SECONDS)
                .build()
        } else {
            httpClient.addInterceptor(headerInterceptor)
                .readTimeout(networkTimeout, TimeUnit.SECONDS)
                .build()
        }

    }

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, client: OkHttpClient, gson: Gson): ApiService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ApiService::class.java)
    }
}