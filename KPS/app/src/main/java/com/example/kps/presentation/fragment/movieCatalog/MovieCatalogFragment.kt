package com.example.kps.presentation.fragment.movieCatalog

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kps.R
import com.example.kps.databinding.FragmentMoviewCatalogBinding
import com.example.kps.domain.model.ApiModel
import com.example.kps.presentation.fragment.movieCatalog.presenter.MovieCatalogAndPresenterContract
import com.example.kps.presentation.fragment.movieCatalog.presenter.MovieCatalogPresenter
import com.example.kps.presentation.adapter.movieCatalog.MovieCatalogRecyclerViewAdapter
import com.example.kps.presentation.adapter.movieCatalog.itemView.BasicItemView
import com.example.kps.presentation.adapter.movieCatalog.itemView.GenresItemView
import com.example.kps.presentation.adapter.movieCatalog.itemView.HeaderItemView
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.example.kps.presentation.adapter.movieCatalog.itemView.GenresSwitchHelper
import com.example.kps.presentation.adapter.movieCatalog.itemView.MovieItemView


class MovieCatalogFragment : Fragment(), MovieCatalogAndPresenterContract.IView {

    private lateinit var binding: FragmentMoviewCatalogBinding
    private val presenter = MovieCatalogPresenter()
    private lateinit var apiData:ApiModel

    private lateinit var adapter: MovieCatalogRecyclerViewAdapter
    private var listItemView = mutableListOf<BasicItemView>()
    private var defaultListItemView = mutableListOf<BasicItemView>()
    private var allGenresMovie = mutableListOf<String>()
    private val switcherGenres = GenresSwitchHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.hookUpAPI()

    }

    private fun initListItemView(apiData: ApiModel){
        val headersOne = HeaderItemView("Жанры")
        val headersTwo = HeaderItemView("Фильмы")
        //блок жанров
        listItemView.add(headersOne)
        for(i in allGenresMovie){
            val genresItemView = GenresItemView(i,switcherGenres,apiData)
            //ЭТО ЛЮТЫЙ КОСТЫЛЬ ДЛЯ ПЕРЕДАЧИ ФИЛЬМОВ. НУЖНО БУДЕТ ИСПРАВЛЯТЬ!!!
            genresItemView.getMovieList { movies->
               val newListItemView = mutableListOf<BasicItemView>()
                for(j in movies){
                    val movieItemView = MovieItemView(j.name, j.image_url)
                    newListItemView.add(movieItemView)
                }
                adapter.reloadDataAdapter((defaultListItemView+newListItemView) as MutableList<BasicItemView>)
            }
            //
            listItemView.add(genresItemView)

        }

        //блок фильмов
        listItemView.add(headersTwo)
        defaultListItemView = listItemView


    }

    private fun initRecView(apiData: ApiModel){
        initListItemView(apiData)
        adapter = MovieCatalogRecyclerViewAdapter(listItemView)
        val mLayoutManager = GridLayoutManager(activity, 2)
        mLayoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (adapter.getItemViewType(position)) {
                    BasicItemView.GENRES_ITEM_VIEW->{
                        return 2
                    }
                    BasicItemView.HEADER_ITEM_VIEW->{
                        return 2
                    }
                    BasicItemView.MOVIE_ITEM_VIEW->{
                        return 1
                    }
                    else -> 0
                }
            }
        }
        binding.movieRecView.layoutManager = mLayoutManager

        binding.movieRecView.hasFixedSize()
        binding.movieRecView.adapter = adapter

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val root = inflater.inflate(R.layout.fragment_moview_catalog, container, false)
        binding = FragmentMoviewCatalogBinding.bind(root)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        presenter.getMovie { movie->
            apiData = movie
            showMovie(apiData)
            binding.loadingPageStatus.visibility = View.GONE
            binding.movieRecView.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.cancelJob()

    }

    override fun showMovie(apiData: ApiModel) {
        for(movie in apiData.films){
            for (genres in movie.genres) {
                if (genres !in allGenresMovie) {
                    allGenresMovie +=genres
                }
            }
        }

        initRecView(apiData)
    }


}