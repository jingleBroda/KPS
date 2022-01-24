package com.example.kps.presentation.fragment.movieCatalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.example.kps.R
import com.example.kps.databinding.FragmentMoviewCatalogBinding
import com.example.domain.domainKPS.model.ApiModel
import com.example.domain.domainKPS.model.FilmsModel
import com.example.kps.presentation.adapter.movieCatalog.MovieCatalogRecyclerViewAdapter
import com.example.kps.presentation.adapter.movieCatalog.itemView.BasicItemView
import com.example.kps.presentation.adapter.movieCatalog.itemView.GenresItemView
import com.example.kps.presentation.adapter.movieCatalog.itemView.HeaderItemView
import com.example.kps.presentation.adapter.movieCatalog.itemView.MovieItemView
import com.example.kps.presentation.fragment.movieCatalog.presenter.MovieCatalogAndPresenterContract
import com.example.kps.presentation.fragment.movieCatalog.presenter.MovieCatalogPresenter
import com.example.kps.presentation.navigation.navigator


class MovieCatalogFragment : Fragment(), MovieCatalogAndPresenterContract.IView {

    private lateinit var binding: FragmentMoviewCatalogBinding
    private val presenter = MovieCatalogPresenter()
    private lateinit var adapter: MovieCatalogRecyclerViewAdapter
    private var listItemView = mutableListOf<BasicItemView>()
    private var allGenresMovie = mutableListOf<String>()

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
        presenter.hookUpAPI()
        presenter.getMovie { movie->
            showMovie(movie)
            binding.loadingPageStatus.visibility = View.GONE
            binding.movieRecView.visibility = View.VISIBLE
            binding.toolbarCatalog.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.cancelJob()
        if(adapter.getActiveItemView() != null){
            adapter.getActiveItemView()!!.cancelJob()
        }
    }

    override fun showMovie(apiData: ApiModel) {
        allGenresMovie = presenter.createGenresList(apiData)
        initRecView(apiData)
    }

    private fun initRecView(apiData: ApiModel){
        if(listItemView.isEmpty()) {
            initListItemView(apiData)
        }
        adapter = MovieCatalogRecyclerViewAdapter(listItemView, allGenresMovie,requireContext(),binding)
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

    private fun initListItemView(apiData: ApiModel){
        //блок жанров
        val headersOne = HeaderItemView("Жанры")
        listItemView.add(headersOne)
        for (i in allGenresMovie) {
            val genresItemView = GenresItemView(i, false)
            listItemView.add(genresItemView)
        }

        //блок фильмов
        val headersTwo = HeaderItemView("Фильмы")
        listItemView.add(headersTwo)
        val sortedListFilm = apiData.films as MutableList<FilmsModel>
        sortedListFilm.sortBy { it.localized_name }
        for (i in sortedListFilm) {
            if (i.genres.isNotEmpty()) {
                val movieItemView = MovieItemView(i)
                listItemView.add(movieItemView)
            }
        }
    }
}