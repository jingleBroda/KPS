package com.example.kps.presentation.adapter.movieCatalog.viewHolderFactory


import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kps.databinding.GenresItemviewBinding
import com.example.kps.databinding.HeaderItemviewBinding
import com.example.kps.databinding.MovieItemviewBinding
import com.example.kps.presentation.adapter.movieCatalog.itemView.BasicItemView


class MovieCatalogViewHolderFactory {

    class GenresViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        private val binding = GenresItemviewBinding.bind(itemView)

        fun bind(concreteMovieGenres: String){
            binding.genresName.text = concreteMovieGenres
        }

    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = HeaderItemviewBinding.bind(itemView)

        fun bind(header:String){
            binding.headerName.text = header
        }
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = MovieItemviewBinding.bind(itemView)

        fun bind(movieName:String, urlImg:String){
            binding.movieName.text = movieName

            Glide
                .with(binding.movieImg)
                .load(urlImg)
                .placeholder(com.example.kps.R.drawable.ic_launcher_foreground)
                .centerCrop()
                .into(binding.movieImg)
        }
    }

    companion object{
        fun create(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
            when (viewType) {
                BasicItemView.GENRES_ITEM_VIEW -> {
                    val inflater = LayoutInflater.from(parent.context)
                    return GenresViewHolder(
                        inflater.inflate(
                            com.example.kps.R.layout.genres_itemview,
                            parent,
                            false
                        )
                    )
                }
                BasicItemView.HEADER_ITEM_VIEW -> {
                    val inflater = LayoutInflater.from(parent.context)
                    return HeaderViewHolder(
                        inflater.inflate(
                            com.example.kps.R.layout.header_itemview,
                            parent,
                            false
                        )
                    )
                }
                BasicItemView.MOVIE_ITEM_VIEW -> {
                    val inflater = LayoutInflater.from(parent.context)
                    return MovieViewHolder(
                        inflater.inflate(
                            com.example.kps.R.layout.movie_itemview,
                            parent,
                            false
                        )
                    )
                }
                else -> {
                    return null
                }
            }
        }
}
}