package com.example.kps.presentation.fragment.movieCatalog.presenter

import com.example.domain.domainKPS.model.ApiModel
import kotlinx.coroutines.CoroutineScope

interface MovieCatalogAndPresenterContract {
    interface IView{
        fun showMovie(apiData: ApiModel)
    }

    interface IPresenter{ //:CoroutineScope{
        fun hookUpAPI()
        fun getMovie(code:(ApiModel)->Unit)
        fun cancelJob()
        fun createGenresList(data:ApiModel):MutableList<String>
    }

}