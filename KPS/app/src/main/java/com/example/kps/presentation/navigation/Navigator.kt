package com.example.kps.presentation.navigation

import androidx.fragment.app.Fragment

fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}

interface Navigator {
    fun showNextScreen(f: Fragment)
    fun showBackScreen()
}