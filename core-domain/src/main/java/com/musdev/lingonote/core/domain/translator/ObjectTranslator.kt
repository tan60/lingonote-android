package com.musdev.lingonote.core.domain.translator

import com.musdev.lingonote.core.data.datasource.DataResponse

interface ObjectTranslator<T, R> {
    fun asDomain(model: T): R
}

fun <T,R> DataResponse<T>.asDomain(translator: ObjectTranslator<T,R>) : DataResponse<R> {
    return when(this){
        is DataResponse.Success-> {
            DataResponse.Success(translator.asDomain(data))
        }
        is DataResponse.Error-> {
            DataResponse.Error(errorCode, errorMessage)
        }
        is DataResponse.Fail -> {
            DataResponse.Fail(failString)
        }
    }
}