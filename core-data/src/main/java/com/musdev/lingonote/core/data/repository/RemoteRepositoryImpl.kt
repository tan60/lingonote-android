package com.musdev.lingonote.core.data.repository

import com.musdev.lingonote.core.data.model.GPTRequestModel
import com.musdev.lingonote.core.data.model.GPTResponseModel

internal class RemoteRepositoryImpl : RemoteRepository {
    override suspend fun queryNoteContent(requestModel: GPTRequestModel): GPTResponseModel {
        TODO("Not yet implemented")
    }
}