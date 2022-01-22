package com.example.kps.presentation.adapter.movieCatalog

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kps.domain.model.ApiModel
import com.example.kps.domain.model.FilmsModel
import com.example.kps.presentation.adapter.movieCatalog.itemView.BasicItemView
import com.example.kps.presentation.adapter.movieCatalog.itemView.GenresItemView
import com.example.kps.presentation.adapter.movieCatalog.itemView.HeaderItemView
import com.example.kps.presentation.adapter.movieCatalog.itemView.MovieItemView
import com.example.kps.presentation.adapter.movieCatalog.viewHolderFactory.MovieCatalogViewHolderFactory
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.properties.Delegates

class MovieCatalogRecyclerViewAdapter(private var typeItemView:MutableList<BasicItemView>,
                                      private var allGenresMovie:MutableList<String>,
                                      private var apiData: ApiModel
):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return typeItemView[position].getItemViewType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieCatalogViewHolderFactory.create(parent, viewType)!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        typeItemView[position].onBindVIewHolder(holder)

        if(typeItemView[position].getItemViewType() == BasicItemView.GENRES_ITEM_VIEW) {

            (typeItemView[position] as GenresItemView).getMovieList { movies ->
                val headersOne = HeaderItemView("Жанры")
                val headersTwo = HeaderItemView("Фильмы")
                val newList = mutableListOf<BasicItemView>()
                newList.add(headersOne)
                for (j in allGenresMovie) {
                    if (j == (typeItemView[position] as GenresItemView).getName()) {
                        val newGenresItemView = GenresItemView(j, true, apiData)
                        newList.add(newGenresItemView)
                    } else {
                        val newGenresItemView = GenresItemView(j, false, apiData)
                        newList.add(newGenresItemView)
                    }
                }
                newList.add(headersTwo)

                Log.d("movies", movies.toString())
                movies.sortBy { it.localized_name }
                Log.d("moviesSorted", movies.toString())

                for(j in movies){
                    val movieItemView = MovieItemView(j.localized_name, j.image_url)
                    newList.add(movieItemView)
                }

                reloadDataAdapter(newList)
            }
        }
    }

    override fun getItemCount(): Int {
       return typeItemView.size
    }

    fun reloadDataAdapter(newList:MutableList<BasicItemView>) {
        typeItemView = newList
        notifyDataSetChanged()
    }


}


