package com.example.kps.presentation.adapter.movieCatalog.itemView

import androidx.recyclerview.widget.RecyclerView

interface BasicItemView {
    companion object{
        const val HEADER_ITEM_VIEW = 0
        const val GENRES_ITEM_VIEW = 1
        const val MOVIE_ITEM_VIEW = 2
    }

    fun getItemViewType():Int
    fun onBindVIewHolder(viewHolder:RecyclerView.ViewHolder)
}