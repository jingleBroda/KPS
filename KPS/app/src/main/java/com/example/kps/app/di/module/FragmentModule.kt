package com.example.kps.app.di.module

import com.example.kps.presentation.fragment.movieCatalog.MovieCatalogFragment
import com.example.kps.presentation.navigation.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun movieCatalog(): MovieCatalogFragment

    @ContributesAndroidInjector
    abstract fun movieCatalogActivity(): MainActivity
}