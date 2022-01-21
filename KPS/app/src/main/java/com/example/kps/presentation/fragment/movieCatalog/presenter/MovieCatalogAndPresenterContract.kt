package com.example.kps.presentation.fragment.movieCatalog.presenter

import com.example.kps.domain.model.ApiModel
import kotlinx.coroutines.CoroutineScope

interface MovieCatalogAndPresenterContract {
    interface IView{
        fun showMovie(movies:ApiModel)
    }

    interface IPresenter:CoroutineScope{
        fun hookUpAPI()
        fun getMovie(code:(ApiModel)->Unit)
        fun cancelJob()
    }

}