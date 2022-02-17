package com.example.shoppinglisttdd.data.remote.response

data class ImageResponse(
    val total:Int,
    val totalHits:Int,
    val hits:List<ImageResult>
)
