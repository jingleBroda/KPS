package com.example.domain.domainKPS.usecase

import com.example.domain.domainKPS.model.ApiModel
import com.example.domain.domainKPS.repository.RepositoryDomain

class HookUpAPIUseCase (private val repository:RepositoryDomain) {
    private var supportGetMovie:((ApiModel)->Unit)? = null

    private fun repositoryHookUpAPI(){
        repository.hookUpAPI(supportGetMovie)
    }

    fun doIt(code:(ApiModel)->Unit){
        supportGetMovie = code
        repositoryHookUpAPI()
    }

    fun cancelJob(){
        repository.cancelJob()
    }
}