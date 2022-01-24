package com.example.kps.app.di.module

import com.example.data.dataKPS.repositoryImpl.RepositoryData
import com.example.domain.domainKPS.repository.RepositoryDomain
import com.example.domain.domainKPS.usecase.HookUpAPIUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    fun createRepository():RepositoryDomain{
        return RepositoryData()
    }

    @Provides
    fun createHookUpAPIUseCase(repositoryData: RepositoryDomain):HookUpAPIUseCase{
        return HookUpAPIUseCase(repositoryData)
    }
}