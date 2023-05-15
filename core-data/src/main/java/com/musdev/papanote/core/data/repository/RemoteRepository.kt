package com.musdev.papanote.core.data.repository

import com.musdev.papanote.core.data.datasource.DataResponse
import com.musdev.papanote.core.data.model.GPTRequestModel
import com.musdev.papanote.core.data.model.GPTResponseDTO

interface RemoteRepository {
    suspend fun queryGPTEdit(apiString: String, apiKey: String, requestBody: GPTRequestModel) : DataResponse<GPTResponseDTO>
}