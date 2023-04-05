package com.example.myapplication.domain

sealed class Endpoint(val urlEndpoint: String){
    object Popular: Endpoint("movie/popular")
    object Top: Endpoint("movie/top_rated")
    object NowPlaying: Endpoint("movie/now_playing")
}