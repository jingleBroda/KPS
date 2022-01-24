package com.example.kps.app.di.module

import com.example.domain.domainKPS.usecase.HookUpAPIUseCase
import com.example.kps.presentation.fragment.movieCatalog.presenter.MovieCatalogPresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {
    @Provides
    fun createMovieCatalogPresenter(hookUpAPIUseCase: HookUpAPIUseCase): MovieCatalogPresenter {
        return MovieCatalogPresenter(hookUpAPIUseCase)
    }
}