package com.example.kps.presentation.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.kps.R
import com.example.kps.presentation.fragment.movieCatalog.MovieCatalogFragment
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity(),Navigator {
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

/*
   к сожалению я так и не смог разобраться, как сохранять состояние активити при смене ориентации экрана
   override fun onSaveInstanceState(outState: Bundle) {
       super.onSaveInstanceState(outState)
   }

   override fun onViewStateRestored(savedInstanceState: Bundle?) {
       super.onViewStateRestored(savedInstanceState)
   }
    */