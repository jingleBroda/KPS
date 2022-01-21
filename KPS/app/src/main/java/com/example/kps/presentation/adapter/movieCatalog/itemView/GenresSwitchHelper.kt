package com.example.kps.presentation.adapter.movieCatalog.itemView

import com.example.kps.presentation.adapter.movieCatalog.itemView.GenresItemView

class GenresSwitchHelper() {

    private var oldAgenresItemViewPressed:GenresItemView? = null

    fun replaceActiveGenresItemView(genresItemViewPressed:GenresItemView){

        if(oldAgenresItemViewPressed == null){
            oldAgenresItemViewPressed = genresItemViewPressed
        }
        else{
            if(oldAgenresItemViewPressed != genresItemViewPressed) {
                oldAgenresItemViewPressed?.setNonActiveItemView()
                oldAgenresItemViewPressed = genresItemViewPressed
            }
        }
    }

}