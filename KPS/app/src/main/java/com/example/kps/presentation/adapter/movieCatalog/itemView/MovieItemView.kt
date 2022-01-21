package com.example.kps.presentation.adapter.movieCatalog.itemView

import androidx.recyclerview.widget.RecyclerView
import com.example.kps.presentation.adapter.movieCatalog.viewHolderFactory.MovieCatalogViewHolderFactory

class MovieItemView(private val movieName:String, private val urlImg:String):BasicItemView {
    override fun getItemViewType(): Int {
        return BasicItemView.MOVIE_ITEM_VIEW
    }

    override fun onBindVIewHolder(viewHolder: RecyclerView.ViewHolder) {
        val movieViewHolder = viewHolder as MovieCatalogViewHolderFactory.MovieViewHolder
        movieViewHolder.bind(movieName, urlImg)
    }
}