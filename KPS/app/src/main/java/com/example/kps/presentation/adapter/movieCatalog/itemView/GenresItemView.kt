package com.example.kps.presentation.adapter.movieCatalog.itemView

import androidx.recyclerview.widget.RecyclerView
import com.example.kps.R
import com.example.kps.data.network.ApiHelper
import com.example.kps.domain.model.ApiModel
import com.example.kps.domain.model.FilmsModel
import com.example.kps.presentation.adapter.movieCatalog.viewHolderFactory.MovieCatalogViewHolderFactory
import kotlinx.coroutines.*
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

class GenresItemView constructor(
    private val genresName:String,
    private val activeStatusParam:Boolean
    ):BasicItemView, CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job+ Dispatchers.Main
    private var result: Response<ApiModel>? = null
    private lateinit var genresViewHolder:MovieCatalogViewHolderFactory.GenresViewHolder
    private var getMovieListLiamda:((MutableList<FilmsModel>, Boolean)->Unit)? = null
    private var concreteGenresFilm = mutableListOf<FilmsModel>()

    override fun getItemViewType(): Int {
        return BasicItemView.GENRES_ITEM_VIEW
    }

    override fun onBindVIewHolder(viewHolder: RecyclerView.ViewHolder) {
        genresViewHolder = viewHolder as MovieCatalogViewHolderFactory.GenresViewHolder
        genresViewHolder.bind(genresName)

        if(activeStatusParam){
            setActiveItemView()
        }
        else{
            setNonActiveItemView()
        }

        genresViewHolder.itemView.setOnClickListener{
            getApiData()
        }
    }

    private fun createFilmsGenresCompilation(allFilms: ApiModel){
        for(i in allFilms.films){
            for(j in i.genres.indices){
                if(i.genres[j] == genresName){
                    concreteGenresFilm.add(i)
                    break
                }
            }
        }
    }

    private fun createFilmsAllGenresCompilation(allFilms: ApiModel){
        for(i in allFilms.films){
            concreteGenresFilm.add(i)
        }
    }

    private fun getApiData(){
        launch {
            result = withContext(Dispatchers.IO){
                ApiHelper.getApi()?.getAllMovie()?.execute()
            }

            if(result != null){
                if(result!!.isSuccessful){
                    if(activeStatusParam){
                        createFilmsAllGenresCompilation(result!!.body()!!)
                        getMovieListLiamda?.invoke(concreteGenresFilm, true)
                    }
                    else{
                        createFilmsGenresCompilation(result!!.body()!!)
                        getMovieListLiamda?.invoke(concreteGenresFilm, false)
                    }

                }
            }

        }
    }

    fun getName():String{
        return genresName
    }

    fun getMovieList(code:((MutableList<FilmsModel>, Boolean)->Unit)){
        getMovieListLiamda = code
    }

    private fun setActiveItemView(){
        genresViewHolder.itemView.setBackgroundResource(R.drawable.background_row_active)
    }

    private fun setNonActiveItemView(){
        genresViewHolder.itemView.setBackgroundResource(R.drawable.background_row_default)
    }
}

