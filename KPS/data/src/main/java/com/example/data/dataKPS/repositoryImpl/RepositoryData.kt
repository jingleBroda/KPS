package com.example.data.dataKPS.repositoryImpl

import android.util.Log
import com.example.data.dataKPS.network.ApiHelper
import com.example.domain.domainKPS.model.ApiModel
import com.example.domain.domainKPS.repository.RepositoryDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

class RepositoryData:RepositoryDomain() {

    private var result: Response<ApiModel>? = null
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job+ Dispatchers.Main

    override fun hookUpAPI(getDataLiamda:((ApiModel)->Unit)?) {
        launch {
            result = withContext(Dispatchers.IO){
                ApiHelper.getApi()?.getAllMovie()?.execute()
            }
            if(result != null){
                if(result!!.isSuccessful){
                    Log.d("TestRepos", "1")
                    getDataLiamda?.invoke(result!!.body()!!)
                }
            }

        }
    }

    override fun cancelJob() {
        job.cancel()
    }

}