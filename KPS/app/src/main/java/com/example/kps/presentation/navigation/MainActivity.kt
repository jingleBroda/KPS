package com.example.kps.presentation.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.kps.R
import com.example.kps.presentation.fragment.movieCatalog.MovieCatalogFragment

class MainActivity : AppCompatActivity(),Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null){
            val fragment = MovieCatalogFragment()
            showStartFragment(fragment)
        }

    }

    private fun showStartFragment(f:Fragment){
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentLayout, f)
            .show(f)
            .commit()
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentLayout, fragment)
            .addToBackStack(null)
            .show(fragment)
            .commit()
    }

    override fun showNextScreen(f: Fragment) {
        launchFragment(f)
    }

    override fun showBackScreen() {
        onBackPressed()
    }
}