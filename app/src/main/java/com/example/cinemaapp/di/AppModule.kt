package com.example.cinemaapp.di

import android.content.Context
import com.example.cinemaapp.data.local.datastore.CouponDataStore
import com.example.cinemaapp.data.local.datastore.MovieDataStore
import com.example.cinemaapp.data.local.datastore.UserPreferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserPreferencesDataStore(
        @ApplicationContext context: Context
    ): UserPreferencesDataStore {
        return UserPreferencesDataStore(context)
    }

    @Provides
    @Singleton
    fun provideMovieDataStore(
        @ApplicationContext context: Context
    ): MovieDataStore {
        return MovieDataStore(context)
    }

    @Provides
    @Singleton
    fun provideCouponDataStore(
        @ApplicationContext context: Context
    ): CouponDataStore {
        return CouponDataStore(context)
    }
}
