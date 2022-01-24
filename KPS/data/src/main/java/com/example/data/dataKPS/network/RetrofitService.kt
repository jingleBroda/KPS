package com.example.data.dataKPS.network


import com.example.domain.domainKPS.model.ApiModel
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {
    @GET("https://s3-eu-west-1.amazonaws.com/sequeniatesttask/films.json")
    fun getAllMovie(): Call<ApiModel>
}