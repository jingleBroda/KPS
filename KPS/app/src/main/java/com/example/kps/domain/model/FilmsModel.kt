package com.example.kps.domain.model

data class FilmsModel(
    val id:Int,
    val localized_name:String,
    val name:String,
    val year:Int,
    val rating:Float,
    val image_url:String,
    val description:String,
    val genres:List<String>
)
