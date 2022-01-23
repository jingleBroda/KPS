package com.example.kps.presentation.adapter.movieCatalog.itemView

import androidx.recyclerview.widget.RecyclerView
import com.example.kps.domain.model.FilmsModel
import com.example.kps.presentation.adapter.movieCatalog.viewHolderFactory.MovieCatalogViewHolderFactory
import com.example.kps.presentation.fragment.movieInformation.MovieInformationFragment

class MovieItemView(private val movie:FilmsModel):BasicItemView {
    private var getFragmentLiamda:((MovieInformationFragment)->Unit)? = null


    override fun getItemViewType(): Int {
        return BasicItemView.MOVIE_ITEM_VIEW
    }

    override fun onBindVIewHolder(viewHolder: RecyclerView.ViewHolder) {
        val movieViewHolder = viewHolder as MovieCatalogViewHolderFactory.MovieViewHolder
        movieViewHolder.bind(movie.localized_name, movie.image_url)
        movieViewHolder.itemView.setOnClickListener{
            getFragmentLiamda?.invoke(MovieInformationFragment.newInstance(
                movie.image_url,
                movie.localized_name,
                movie.name,
                movie.year,
                movie.rating,
                movie.description
            ))
        }
    }

    fun getFragment(code:(MovieInformationFragment)->Unit){
        getFragmentLiamda = code
    }
}