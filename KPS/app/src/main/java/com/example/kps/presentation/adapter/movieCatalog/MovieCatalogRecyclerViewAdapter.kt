package com.example.kps.presentation.adapter.movieCatalog

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kps.domain.model.FilmsModel
import com.example.kps.presentation.adapter.movieCatalog.itemView.BasicItemView
import com.example.kps.presentation.adapter.movieCatalog.itemView.GenresItemView
import com.example.kps.presentation.adapter.movieCatalog.itemView.MovieItemView
import com.example.kps.presentation.adapter.movieCatalog.viewHolderFactory.MovieCatalogViewHolderFactory
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.properties.Delegates

class MovieCatalogRecyclerViewAdapter(private var typeItemView:MutableList<BasicItemView>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return typeItemView[position].getItemViewType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //тут нужно сделать обработку null в будущем
        return MovieCatalogViewHolderFactory.create(parent, viewType)!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        typeItemView[position].onBindVIewHolder(holder)
        if(typeItemView[position].getItemViewType() == BasicItemView.GENRES_ITEM_VIEW){
            val asGenresType = typeItemView[position] as GenresItemView
            asGenresType.getMovieList { movie->
                reloadDataAdapter(movie)
            }
        }
    }

    private fun reloadDataAdapter(movie: MutableList<FilmsModel>){
        //мысль на потом, нужно сделать обновление адаптера из вне (в фрагменте), мб это устранит эксепшн

        /*
        for(i in typeItemView.indices){//i in typeItemView
            if(typeItemView[i].getItemViewType() == BasicItemView.MOVIE_ITEM_VIEW)
            {
                typeItemView.remove(typeItemView[i])
            }
        }

        for(i in movie.indices){
            val movieItemView = MovieItemView(movie[i].name, movie[i].image_url)
            typeItemView.add(movieItemView)
        }
        notifyDataSetChanged()

         */
    }

    override fun getItemCount(): Int {
       return typeItemView.size
    }


}


