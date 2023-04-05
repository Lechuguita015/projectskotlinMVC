package com.example.myapplication.ui

import android.app.AlertDialog
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMoviesBinding
import com.example.myapplication.databinding.MovieItemBinding
import com.example.myapplication.domain.BASE_IMG
import com.example.myapplication.domain.StatusRequestEnum
import com.example.myapplication.domain.movie.MovieModel
import com.example.myapplication.showAlert
import com.example.myapplication.utils.BaseActivity
import com.example.myapplication.utils.GAdapter
import com.example.myapplication.viewmodel.MoviesVIewModel

class MoviesActivity : BaseActivity<ActivityMoviesBinding> () {

    private val viewModel: MoviesVIewModel by viewModels()

    override fun getLayout() = R.layout.activity_movies

    override fun initView() {
        getMovies()
    }

    private val adapter = GAdapter <MovieItemBinding, MovieModel>(R.layout.movie_item,
        AsyncDifferConfig.Builder(object: DiffUtil.ItemCallback<MovieModel>(){
            override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel) = oldItem == newItem

            override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel) = oldItem.id == newItem.id

        }).build(), holderCallback = { bindingItem, model, _, _, position ->
            Glide.with(this).load("$BASE_IMG${model.poster_path}").into(bindingItem.ivMovie)
        }
    )

    private fun getMovies(){
        binding.rvMovies.adapter = adapter
        viewModel.getMovies()
    }

    override fun initObservers() {
        viewModel.popularMovies.observe(this){
            if(it != null){
                when (it.statusRequest){
                    StatusRequestEnum.SUCCESS -> {
                        Log.e("SUCCESS", "Satisfactorio${it.successData?.results}")
                        it.successData?.let { moviesResponse ->
                            adapter.submitList(moviesResponse.results)
                        }
                    }
                    StatusRequestEnum.FAILURE -> {
                        Log.e("FAILURE", "Fallo${it.successData?.results}")
                        AlertDialog.Builder(this)
                            .setTitle("ERROR AL CARGAR").setMessage(it.errorData ?: "Error").setNegativeButton("RECHAZAR") { dialogInterface, i ->

                            }.setPositiveButton("OK") { dialogInterface, i ->

                            }.create().show()
                    }
                    StatusRequestEnum.LOADING -> {
                        Log.e("LOADING", "Cargando${it.successData?.results}")
                }
                    else -> {

                    }
                }
            }
        }
    }

}