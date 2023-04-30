package com.musdev.lingonote.core.data.repository

import com.musdev.lingonote.core.data.datasource.DataResponse
import com.musdev.lingonote.core.data.model.GPTRequestModel
import com.musdev.lingonote.core.data.model.GPTResponseDTO

interface RemoteRepository {
    suspend fun queryGPTEdit(apiString: String, apiKey: String, requestBody: GPTRequestModel) : DataResponse<GPTResponseDTO>
}