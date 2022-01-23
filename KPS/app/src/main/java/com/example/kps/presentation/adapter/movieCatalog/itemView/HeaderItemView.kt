package com.example.kps.presentation.adapter.movieCatalog.itemView

import androidx.recyclerview.widget.RecyclerView
import com.example.kps.presentation.adapter.movieCatalog.viewHolderFactory.MovieCatalogViewHolderFactory

class HeaderItemView(private val headerText:String):BasicItemView {
    override fun getItemViewType(): Int {
        return BasicItemView.HEADER_ITEM_VIEW
    }

    override fun onBindVIewHolder(viewHolder: RecyclerView.ViewHolder) {
        val headerViewHolder = viewHolder as MovieCatalogViewHolderFactory.HeaderViewHolder
        headerViewHolder.bind(headerText)
    }
}