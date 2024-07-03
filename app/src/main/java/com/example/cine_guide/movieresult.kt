package com.example.cine_guide

sealed class movieresult<T>(
    val data:T? = null,
    val message:String? = null
) {
    class Success<T> (data:T?) : movieresult<T>(data)
    class Error<T> (data:T? = null, message: String?) : movieresult<T>(data, message)
}