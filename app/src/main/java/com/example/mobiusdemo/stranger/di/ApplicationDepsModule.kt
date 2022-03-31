package com.example.mobiusdemo.stranger.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ApplicationDepsModule {

    companion object {

        @JvmStatic
        @Provides
        @Singleton
        fun providesRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl("https://namo-memes.herokuapp.com/")
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(Date::class.java, Rfc3339DateJsonAdapter())
                        .build()
                )
            )
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }
}
