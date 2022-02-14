package com.example.kps.app.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kps.app.di.utils.ViewModelFactory
import com.example.kps.app.di.utils.ViewModelKey
import com.example.kps.presentation.fragment.movieCatalog.MovieCatalogViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MovieCatalogViewModel::class)
    internal abstract fun bindMonetaryRateMenuFragmentViewModel(monetaryRateMenuFragmentViewModel: MovieCatalogViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}