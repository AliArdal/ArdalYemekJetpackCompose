package com.example.graduationproject.di

import com.example.graduationproject.data.datasource.YemeklerDataSource
import com.example.graduationproject.data.repo.YemeklerRepository
import com.example.graduationproject.retrofit.ApiUtils
import com.example.graduationproject.retrofit.YemeklerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideYemeklerDao(): YemeklerDao {
        return ApiUtils.getYemeklerDao()
    }

    @Provides
    @Singleton
    fun provideYemeklerDataSource(ydao: YemeklerDao): YemeklerDataSource {
        return YemeklerDataSource(ydao)
    }

    @Provides
    @Singleton
    fun provideYemeklerRepository(yds: YemeklerDataSource): YemeklerRepository {
        return YemeklerRepository(yds)
    }
}
