package com.example.kps.presentation.fragment.movieCatalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.domainKPS.model.ApiModel
import com.example.domain.domainKPS.usecase.HookUpAPIUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieCatalogViewModel @Inject constructor(private val hookUpAPIUseCase: HookUpAPIUseCase): ViewModel() {

    private var _supportGetMovie = MutableSharedFlow<ApiModel>()
    val supportGetMovie = _supportGetMovie.asSharedFlow()

    fun hookUpAPI() {
        hookUpAPIUseCase.doIt{ apiModel->
            viewModelScope.launch {
                _supportGetMovie.emit(apiModel)
            }
        }
    }

    fun createGenresList(data: ApiModel): MutableList<String> {
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