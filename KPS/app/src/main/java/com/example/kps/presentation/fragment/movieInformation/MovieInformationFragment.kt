package com.example.kps.presentation.fragment.movieInformation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.kps.R
import com.example.kps.databinding.FragmentMovieInformationBinding
import com.example.kps.presentation.navigation.navigator

class MovieInformationFragment : Fragment() {

    private lateinit var binding:FragmentMovieInformationBinding
    private var movieImg = ""
    private var movieName = ""
    private var movieNameOrigin = ""
    private var movieYears = 0
    private var movieRanked = 0.0F
    private var movieDescript = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_movie_information, container, false)
        binding = FragmentMovieInformationBinding.bind(root)

        movieImg = requireArguments().get(movieImgKey) as String
        movieName = requireArguments().get(movieNameKey) as String
        movieNameOrigin = requireArguments().get(movieNameOriginKey) as String
        movieYears = requireArguments().get(movieYearsKey) as Int
        movieRanked = requireArguments().get(movieRankedKey) as Float
        movieDescript = requireArguments().get(movieDescriptKey) as String

        binding.toolbarMovieInfo.title = movieName
        binding.toolbarMovieInfo.setNavigationOnClickListener{
            navigator().showBackScreen()
        }

        Glide
            .with(binding.previewMovie)
            .load(movieImg)
            .placeholder(R.drawable.ic_launcher_foreground)
            .centerCrop()
            .into(binding.previewMovie)
        binding.descriptMovie.text = movieDescript
        binding.originalNameMovie.text = movieNameOrigin
        binding.rankedMovie.text = resources.getString(R.string.movie_ranked)+" "+movieRanked.toString()
        binding.yearsMovie.text = resources.getString(R.string.movie_year)+" "+movieYears.toString()

        return binding.root
    }

    companion object{
        const val movieImgKey = "movieImg"
        const val movieNameKey = "movieName"
        const val movieNameOriginKey = "movieNameOriginKey"
        const val movieYearsKey = "movieYears"
        const val movieRankedKey = "movieRanked"
        const val movieDescriptKey= "movieDescript"

        fun newInstance(
            img:String,
            name:String,
            nameOrigin:String,
            years:Int,
            ranked:Float,
            descript:String
        ): MovieInformationFragment {
            val movieInformationFragment = MovieInformationFragment()
            val args = Bundle()
            args.putString(movieImgKey, img)
            args.putString(movieNameKey, name)
            args.putString(movieNameOriginKey, nameOrigin)
            args.putInt(movieYearsKey, years)
            args.putFloat(movieRankedKey, ranked)
            args.putString(movieDescriptKey, descript)
            movieInformationFragment.arguments = args
            return movieInformationFragment
        }
    }
}