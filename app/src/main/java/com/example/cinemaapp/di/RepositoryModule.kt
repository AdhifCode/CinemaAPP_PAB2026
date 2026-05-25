package com.example.cinemaapp.di

import com.example.cinemaapp.data.local.datastore.CouponDataStore
import com.example.cinemaapp.data.local.datastore.MovieDataStore
import com.example.cinemaapp.data.local.datastore.UserPreferencesDataStore
import com.example.cinemaapp.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        userPrefsDataStore: UserPreferencesDataStore
    ): UserRepository {
        return UserRepositoryImpl(userPrefsDataStore)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        movieDataStore: MovieDataStore
    ): MovieRepository {
        return MovieRepositoryImpl(movieDataStore)
    }

    @Provides
    @Singleton
    fun provideCouponRepository(
        couponDataStore: CouponDataStore
    ): CouponRepository {
        return CouponRepositoryImpl(couponDataStore)
    }

    @Provides
    @Singleton
    fun provideSeatRepository(): SeatRepository {
        return SeatRepositoryImpl()
    }
}