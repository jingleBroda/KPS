package com.example.kps.presentation.adapter.movieCatalog

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kps.databinding.FragmentMoviewCatalogBinding
import com.example.kps.presentation.adapter.movieCatalog.itemView.BasicItemView
import com.example.kps.presentation.adapter.movieCatalog.itemView.GenresItemView
import com.example.kps.presentation.adapter.movieCatalog.itemView.HeaderItemView
import com.example.kps.presentation.adapter.movieCatalog.itemView.MovieItemView
import com.example.kps.presentation.adapter.movieCatalog.viewHolderFactory.MovieCatalogViewHolderFactory
import com.example.kps.presentation.navigation.Navigator

class MovieCatalogRecyclerViewAdapter
    constructor(
        private var typeItemView:MutableList<BasicItemView>,
        private var allGenresMovie:MutableList<String>,
        private val context: Context,
        private val rootBinding:FragmentMoviewCatalogBinding
    ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private  var activeGenres:GenresItemView? = null

    override fun getItemViewType(position: Int): Int {
        return typeItemView[position].getItemViewType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieCatalogViewHolderFactory.create(parent, viewType)!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        typeItemView[position].onBindVIewHolder(holder)
        if(typeItemView[position].getItemViewType() == BasicItemView.GENRES_ITEM_VIEW) {
            (typeItemView[position] as GenresItemView).setProgresBar {
                setStatusProgressBar(it)
            }
        //обработка получения фильмов выбранного жанра
            (typeItemView[position] as GenresItemView).getMovieListOnCLickGenresItemView {
            movies, doublePressingStatus ->
                val newList = mutableListOf<BasicItemView>()
                val headersOne = HeaderItemView("Жанры")
                newList.add(headersOne)
                for (i in allGenresMovie) {
                    if (i == (typeItemView[position] as GenresItemView).getName()) {
                        if(doublePressingStatus){
                            val newGenresItemView = GenresItemView(i, false)
                            newList.add(newGenresItemView)
                        }
                        else{
                            val newGenresItemView = GenresItemView(i, true)
                            newList.add(newGenresItemView)
                            activeGenres = newGenresItemView
                        }
                    } else {
                        val newGenresItemView = GenresItemView(i, false)
                        newList.add(newGenresItemView)
                    }
                }
                val headersTwo = HeaderItemView("Фильмы")
                newList.add(headersTwo)
                movies.sortBy { it.localized_name }
                for(j in movies){
                    if(j.genres.isNotEmpty()){
                        val movieItemView = MovieItemView(j)
                        newList.add(movieItemView)
                    }
                }
                reloadDataAdapter(newList)
            }
        }
        else{
            if(typeItemView[position].getItemViewType() == BasicItemView.MOVIE_ITEM_VIEW){
                (typeItemView[position] as MovieItemView).moveMovieInformationFragmentOnClickMovieItemView {
                    (context as Navigator).showNextScreen(it)
                }
            }
        }
    }

    fun getActiveItemView():GenresItemView?{
        return activeGenres
    }

    override fun getItemCount(): Int {
       return typeItemView.size
    }

    private fun reloadDataAdapter(newList:MutableList<BasicItemView>) {
        typeItemView = newList
        notifyDataSetChanged()
    }

    private fun setStatusProgressBar(pr:Boolean){
        if(pr){
            rootBinding.loadingPageStatus.visibility = View.VISIBLE
        }
        else{
            rootBinding.loadingPageStatus.visibility = View.GONE
        }
    }


}


