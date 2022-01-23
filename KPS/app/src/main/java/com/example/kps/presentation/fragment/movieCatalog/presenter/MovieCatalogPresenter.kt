package com.example.kps.presentation.fragment.movieCatalog.presenter


import com.example.kps.domain.model.ApiModel
import com.example.kps.data.network.ApiHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

class MovieCatalogPresenter :MovieCatalogAndPresenterContract.IPresenter {

    private var supportGetMovie:((ApiModel)->Unit)? = null
    private var result: Response<ApiModel>? = null
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job+Dispatchers.Main

    override fun hookUpAPI() {
        launch {
            result = withContext(Dispatchers.IO){
                ApiHelper.getApi()?.getAllMovie()?.execute()
            }
            if(result != null){
                if(result!!.isSuccessful){
                    supportGetMovie?.invoke(result!!.body()!!)
                }
            }

        }

    }

    override fun getMovie(code:(ApiModel)->Unit) {
        supportGetMovie = code
    }

    override fun cancelJob() {
        job.cancel()
    }

}