package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.datasource.remove.PopularSource
import com.example.myapplication.domain.DataResponse
import com.example.myapplication.domain.StatusRequestEnum
import com.example.myapplication.domain.movie.MoviesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesVIewModel:ViewModel() {

    val popularMovies:LiveData<DataResponse<MoviesResponse>>
    get() = _popularMovies

    private val _popularMovies = MutableLiveData<DataResponse<MoviesResponse>>()


    fun getMovies(){
        _popularMovies.value = DataResponse(statusRequest = StatusRequestEnum.LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            _popularMovies.postValue(PopularSource.getData())
        }
    }
}