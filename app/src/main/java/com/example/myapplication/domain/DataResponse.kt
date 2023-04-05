package com.example.myapplication.domain

data class DataResponse<R>(
    val statusRequest: StatusRequestEnum = StatusRequestEnum.NONE,
    val successData: R? = null,
    val errorModel: ErrorModel? = null,
    var errorData: String? = null
)
