package com.example.domain.domainKPS.repository

import com.example.domain.domainKPS.model.ApiModel
import kotlinx.coroutines.CoroutineScope

abstract class RepositoryDomain:CoroutineScope {
    abstract fun hookUpAPI(getDataLiamda:((ApiModel)->Unit)?)
    abstract fun cancelJob()
}