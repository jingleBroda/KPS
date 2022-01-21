package com.example.kps.presentation.fragment.movieInformation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kps.R
import com.example.kps.databinding.FragmentMovieInformationBinding

class MovieInformationFragment : Fragment() {

    lateinit var binding:FragmentMovieInformationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val root = inflater.inflate(R.layout.fragment_movie_information, container, false)
        binding = FragmentMovieInformationBinding.bind(root)

        return binding.root
    }


}