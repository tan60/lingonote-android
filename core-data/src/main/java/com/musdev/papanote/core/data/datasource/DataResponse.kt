package com.musdev.papanote.core.data.datasource


sealed class DataResponse<out T> {
    data class Success<out T>(val data: T): DataResponse<T>()
    data class Error(val errorCode: Int?, val errorMessage: String?): DataResponse<Nothing>()
    data class Fail(val failString: String?): DataResponse<Nothing>()
}
