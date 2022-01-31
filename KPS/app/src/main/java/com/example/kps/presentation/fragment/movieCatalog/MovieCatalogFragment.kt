package com.example.kps.presentation.fragment.movieCatalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.example.domain.domainKPS.model.ApiModel
import com.example.domain.domainKPS.model.FilmsModel
import com.example.kps.R
import com.example.kps.databinding.FragmentMoviewCatalogBinding
import com.example.kps.presentation.adapter.movieCatalog.MovieCatalogRecyclerViewAdapter
import com.example.kps.presentation.adapter.movieCatalog.itemView.BasicItemView
import com.example.kps.presentation.adapter.movieCatalog.itemView.GenresItemView
import com.example.kps.presentation.adapter.movieCatalog.itemView.HeaderItemView
import com.example.kps.presentation.adapter.movieCatalog.itemView.MovieItemView
import com.example.kps.presentation.fragment.movieCatalog.presenter.MovieCatalogAndPresenterContract
import com.example.kps.presentation.fragment.movieCatalog.presenter.MovieCatalogPresenter
import dagger.android.support.DaggerFragment
import java.io.IOException
import javax.inject.Inject


class MovieCatalogFragment : DaggerFragment(), MovieCatalogAndPresenterContract.IView {

    @Inject
    lateinit var presenter:MovieCatalogPresenter
    private lateinit var binding: FragmentMoviewCatalogBinding
    private var adapter: MovieCatalogRecyclerViewAdapter? = null
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

        if(isReallyOnline()){
            presenter.hookUpAPI()
            presenter.getMovie { apiData->
                showMovie(apiData)
                binding.loadingPageStatus.visibility = View.GONE
                binding.movieRecView.visibility = View.VISIBLE
                binding.toolbarCatalog.visibility = View.VISIBLE
            }
        }
        else{
            Toast.makeText(
                requireContext(),
                "Интернет-соединение отсутствует! Возможно, доступ к интернету ограничен, или он отсутствует.",
                Toast.LENGTH_LONG
            ).show()
            binding.loadingPageStatus.visibility = View.GONE
            binding.movieRecView.visibility = View.VISIBLE
            binding.toolbarCatalog.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        presenter.cancelJob()
        if(adapter?.getActiveItemView() != null){
            adapter?.getActiveItemView()!!.cancelJob()
        }
        super.onDestroy()
    }

    override fun showMovie(apiData: ApiModel) {
        allGenresMovie = presenter.createGenresList(apiData)
        initRecView(apiData)
    }

    private fun initRecView(apiData: ApiModel){
        if(listItemView.isEmpty()) {
            initListItemView(apiData)
            adapter = MovieCatalogRecyclerViewAdapter(listItemView, allGenresMovie,requireContext(),binding)
        }
        else{
           adapter?.replaceRootElem(binding)
        }
        settingAdapter()
    }

    private fun settingAdapter(){
        val mLayoutManager = GridLayoutManager(activity, 2)
        mLayoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (adapter?.getItemViewType(position)) {
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

    //проверка интернет соединения
    private fun isReallyOnline(): Boolean {
        val runtime = Runtime.getRuntime()
        try {
            val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
            val exitValue = ipProcess.waitFor()
            return exitValue == 0
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return false
    }
}