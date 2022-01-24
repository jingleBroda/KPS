package com.example.kps.app.di

import com.example.kps.app.App
import com.example.kps.app.di.module.FragmentModule
import com.example.kps.app.di.module.PresenterModule
import com.example.kps.app.di.module.RepositoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(modules = [
    AndroidSupportInjectionModule::class,
    AndroidInjectionModule::class,
    PresenterModule::class,
    RepositoryModule::class,
    FragmentModule::class
])
@Singleton
interface AppComponent:AndroidInjector<App> {
    override fun inject(app: App)
    @Component.Builder
    interface Builder{
        @BindsInstance
        fun bindContext(app: App):Builder
        fun build():AppComponent
    }
}