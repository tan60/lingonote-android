package com.musdev.lingonote.core.data.repository

import com.musdev.lingonote.core.data.model.GPTRequestModel
import com.musdev.lingonote.core.data.services.api.ApiResponse
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteRepo: RemoteRepository
    )
{
    suspend fun improveNoteContent(content: String) : ApiResponse<Any> {

        var requestModel = GPTRequestModel().apply {
            this.model = "text-davinci-edit-001"
            this.input = "Hi, this is DoHyoung Kim and I am Android developer. Am I doing now?"
            this.instruction = "correct and improve only grammar."
        }

        return remoteRepo.queryNoteContent(requestModel)
    }
}