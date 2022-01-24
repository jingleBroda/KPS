package com.example.kps.presentation.fragment.movieCatalog.presenter


import com.example.domain.domainKPS.model.ApiModel
import com.example.domain.domainKPS.usecase.HookUpAPIUseCase
import javax.inject.Inject

class MovieCatalogPresenter @Inject constructor(private val hookUpAPIUseCase: HookUpAPIUseCase) :MovieCatalogAndPresenterContract.IPresenter {
    private var supportGetMovie:((ApiModel)->Unit)? = null

    override fun hookUpAPI() {
        hookUpAPIUseCase.doIt{ apiModel->
            supportGetMovie?.invoke(apiModel)
        }
    }

    override fun getMovie(code:(ApiModel)->Unit) {
        supportGetMovie = code
    }

    override fun cancelJob() {
        hookUpAPIUseCase.cancelJob()
    }

    override fun createGenresList(data: ApiModel): MutableList<String> {
        val result = mutableListOf<String>()
        for(movie in data.films){
            for (genres in movie.genres) {
                if (genres !in result) {
                    result +=genres
                }
            }
        }
        return result
    }

}