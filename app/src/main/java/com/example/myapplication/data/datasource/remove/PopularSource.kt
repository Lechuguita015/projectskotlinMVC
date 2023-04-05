package com.example.myapplication.data.datasource.remove

import com.example.myapplication.domain.*
import com.example.myapplication.domain.movie.MoviesResponse
import retrofit2.Call
import java.net.UnknownHostException

class PopularSource {

    companion object {
        fun getData():DataResponse<MoviesResponse> = try {
            ValidResponse<MoviesResponse>(MoviesResponse::class).validationMethod(
                InitServices<Call<MoviesResponse>>().executeService(
                    "$BASE_URL${Endpoint.Popular.urlEndpoint}"
                )
            )
        } catch (exception:Exception){
            if(exception is UnknownHostException){
                DataResponse(
                    statusRequest = StatusRequestEnum.FAILURE,
                    null,
                    errorData = "El servidor tuvo un error"
                )
            }else {
                DataResponse(
                    statusRequest = StatusRequestEnum.FAILURE,
                    null,
                    errorData = "Servidor Entrante"
                )
            }
        }

    }
}