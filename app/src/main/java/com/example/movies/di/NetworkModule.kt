package com.example.movies.di

import android.util.Log
import com.example.movies.data.remote.apis.MoviesAPI
import com.example.movies.util.Constants.Companion.API_KEY
import com.example.movies.util.Constants.Companion.BASE_URL
import com.example.movies.util.Constants.Companion.LANGUAGE
import com.example.movies.util.Constants.Companion.PARAM_API_KEY
import com.example.movies.util.Constants.Companion.PARAM_LANGUAGE
import com.example.movies.util.Constants.Companion.TAG_OK_HTTP
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideMoviesApi(retrofit: Retrofit) = retrofit.create(MoviesAPI::class.java)

    @Provides
    @Singleton
    fun provideRetrofitInstance(
        baseUrl: String,
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ) = Retrofit
        .Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(gsonConverterFactory)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideBaseUrl() = BASE_URL

    @Provides
    @Singleton
    fun provideConvertFactory() = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        @LangInterceptor langInterceptor: Interceptor,
        @AuthInterceptor authInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(langInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor() = HttpLoggingInterceptor { message ->
        Log.e(
            TAG_OK_HTTP,
            message
        )
    }.setLevel(HttpLoggingInterceptor.Level.BODY)


    @Provides
    @Singleton
    @LangInterceptor
    fun provideLangInterceptor() = Interceptor { chain ->
        var request = chain.request()
        val url =
            request
                .url
                .newBuilder()
                .addQueryParameter(PARAM_LANGUAGE, LANGUAGE)
                .build()
        request = request
            .newBuilder()
            .url(url)
            .build()
        chain.proceed(request)
    }

    @Provides
    @Singleton
    @AuthInterceptor
    fun provideAuthInterceptor() = Interceptor { chain ->
        var request = chain.request()
        val url =
            request
                .url
                .newBuilder()
                .addQueryParameter(PARAM_API_KEY, API_KEY)
                .build()
        request = request
            .newBuilder()
            .url(url)
            .build()
        chain.proceed(request)
    }

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LangInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthInterceptor