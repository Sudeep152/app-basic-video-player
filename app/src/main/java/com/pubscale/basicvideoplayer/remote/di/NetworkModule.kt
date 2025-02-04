package com.pubscale.basicvideoplayer.remote.di

import com.pubscale.basicvideoplayer.remote.api.VideoApi
import com.pubscale.basicvideoplayer.remote.utils.ConstantData.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    // Provides an instance of HttpLoggingInterceptor with BODY level logging
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    // Provides a single OkHttpClient instance configured with the logging interceptor
    @Provides
    @Singleton
    fun okHttpClient(provideLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(provideLoggingInterceptor)
            .build()
    }

    // Provides a Retrofit instance configured with Moshi converter and the base URL
    @Provides
    @Singleton
    fun provideRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
    }

    // Provides an instance of VideoApi using the configured Retrofit instance
    @Provides
    @Singleton
    fun provideVideoPlayerApi(retrofit: Retrofit): VideoApi {
        return retrofit.create(VideoApi::class.java)
    }
}