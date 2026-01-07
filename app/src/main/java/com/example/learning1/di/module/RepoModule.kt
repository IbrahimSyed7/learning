package com.example.learning1.di.module

import com.example.learning1.data.repo.AuthRepoImpl
import com.example.learning1.domain.repo.AuthRepo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepoModule {


    @Binds
    @ViewModelScoped
    abstract fun providesRepo(authRepoImpl: AuthRepoImpl) : AuthRepo

}