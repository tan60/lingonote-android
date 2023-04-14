package com.musdev.lingonote.core.data.repository

import com.musdev.lingonote.core.data.model.GPTRequestModel
import com.musdev.lingonote.core.data.services.api.ApiResponse

interface RemoteRepository {

    suspend fun queryNoteContent(requestModel: GPTRequestModel) : ApiResponse<Any>
}