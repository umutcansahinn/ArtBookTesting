package com.umutcansahin.artbooktesting2.model

data class ImageResponce(
    val hits: List<ImageResult>,
    val total: Int,
    val totalHits: Int
)