package com.example.myapplication.domain

class InitServices<R> {
    fun executeService(endPoint: String) =
        RetroClient.getRestEngine().create(ApiServices::class.java)
            .serviceResponseBody(endPoint) as R
}
