package com.pubscale.basicvideoplayer.di

import com.pubscale.basicvideoplayer.repository.BaseVideoPlayerRepository
import com.pubscale.basicvideoplayer.repository.BaseVideoPlayerRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideBaseVideoRepository(baseVideoPlayerRepository: BaseVideoPlayerRepositoryImpl): BaseVideoPlayerRepository {
        return baseVideoPlayerRepository
    }
}