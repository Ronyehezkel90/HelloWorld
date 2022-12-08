package com.ronyehezkel.helloworld.di

import android.app.Application
import com.ronyehezkel.helloworld.IRepository
import com.ronyehezkel.helloworld.model.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MyModule {

    @Provides
    fun provideRepository(context: Application): IRepository {
        return Repository(context)
    }

}