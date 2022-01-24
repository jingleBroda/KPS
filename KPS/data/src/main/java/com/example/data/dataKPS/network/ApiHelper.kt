package com.example.data.dataKPS.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiHelper {
    private var api: RetrofitService? = null
    private val baseUrl = "https://baseUrl/"

    fun getApi(): RetrofitService?{
        if(api ==null){
            initApi()
        }
        return api
    }

    private fun initApi(){
        api = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(RetrofitService::class.java)
    }
}