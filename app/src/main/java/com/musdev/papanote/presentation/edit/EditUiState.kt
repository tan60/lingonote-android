package com.musdev.papanote.presentation.edit

data class EditUiState (
    var topic: String = "",
    var content: String = "",
    var isPosting: Boolean = false,
    var isPostComplete: Boolean = false,
    var isSaveEnable: Boolean = topic.isNotEmpty() && content.isNotEmpty(),
    var isPreviewEnable: Boolean = topic.isNotEmpty() && content.isNotEmpty()
)