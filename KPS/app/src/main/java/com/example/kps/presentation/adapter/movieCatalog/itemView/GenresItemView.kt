package com.example.kps.presentation.adapter.movieCatalog.itemView

import androidx.recyclerview.widget.RecyclerView
import com.example.kps.R
import com.example.kps.domain.model.ApiModel
import com.example.kps.domain.model.FilmsModel
import com.example.kps.presentation.adapter.movieCatalog.viewHolderFactory.MovieCatalogViewHolderFactory
import java.util.concurrent.CopyOnWriteArrayList

class GenresItemView constructor(
    private val genresName:String,
    private val switcher: GenresSwitchHelper, //private
    allFilms: ApiModel
    ):BasicItemView {

    private lateinit var genresViewHolder:MovieCatalogViewHolderFactory.GenresViewHolder
    private var getMovieListLiamda:((MutableList<FilmsModel>)->Unit)? = null
    private var concreteGenresFilm = mutableListOf<FilmsModel>()

    init{
        createFilmsGenresCompilation(allFilms)
    }

    override fun getItemViewType(): Int {
        return BasicItemView.GENRES_ITEM_VIEW
    }

    override fun onBindVIewHolder(viewHolder: RecyclerView.ViewHolder) {
        genresViewHolder = viewHolder as MovieCatalogViewHolderFactory.GenresViewHolder
        genresViewHolder.bind(genresName)

        genresViewHolder.itemView.setOnClickListener{
            setActiveItemView()
            getMovieListLiamda?.invoke(concreteGenresFilm)
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

    fun getMovieList(code:((MutableList<FilmsModel>)->Unit)){
        getMovieListLiamda = code
    }

    fun setActiveItemView(){
        genresViewHolder.itemView.setBackgroundResource(R.drawable.background_row_active)
        switcher.replaceActiveGenresItemView(this)
    }

    fun setNonActiveItemView(){
        genresViewHolder.itemView.setBackgroundResource(R.drawable.background_row_default)
    }
}

