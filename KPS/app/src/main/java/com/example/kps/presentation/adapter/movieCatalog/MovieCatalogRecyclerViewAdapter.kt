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
        return MovieCatalogViewHolderFactory.create(parent, viewType)!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        typeItemView[position].onBindVIewHolder(holder)
    }

    override fun getItemCount(): Int {
       return typeItemView.size
    }

    fun reloadDataAdapter(newTypeItemView:MutableList<BasicItemView>){
        typeItemView = newTypeItemView
        notifyDataSetChanged()
    }


}


