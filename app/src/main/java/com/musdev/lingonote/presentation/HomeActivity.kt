package com.musdev.lingonote.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.musdev.lingonote.presentation.edit.EditViewModel
import com.musdev.lingonote.presentation.home.HomeScreen
import com.musdev.lingonote.presentation.notes.NotesViewModel
import com.musdev.lingonote.presentation.preview.PreviewViewModel
import com.musdev.lingonote.ui.theme.LingoNoteTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "AppDebug"
@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    @Inject
    lateinit var noteViewModel: NotesViewModel

    @Inject
    lateinit var editViewModel: EditViewModel

    @Inject
    lateinit var previewViewModel: PreviewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //test code
        /*lifecycleScope.launch(Dispatchers.IO) {
            val startIndex = getTotalCountTest()

            for (i in startIndex.. startIndex + 3) {
                postNoteTest("This is dummy topic $i \nprovided by the test code", "This is dummy contents  $i provided by the the code. \n This is dummy contents  $i provided by the the code. \n This is dummy contents  $i provided by the the code. ")
            }
        }*/

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                noteViewModel.fetchNotesAtFirst()
            }
        }

        setContent {
            LingoNoteTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen(
                        modifier = Modifier.fillMaxSize(),
                        notesViewModel = noteViewModel,
                        editViewModel = editViewModel,
                        previewViewModel = previewViewModel
                    )
                }
            }
        }
    }

    /*private suspend fun getTotalCountTest(): Int {
        return noteUseCase.getTotalNoteCount()
    }
    private suspend fun postNoteTest(topic: String, content: String) {
        val noteEntity = NoteEntity().apply {
            this.topic = topic
            this.content = content
        }

        noteUseCase.postNote(noteEntity)
    }*/

    /*private suspend fun correctContentTest(content: String) {
        var correctEntity = correctContentUseCase.correctMyContent(content)
        when (correctEntity.isSuccess) {
            true -> {
                Log.d(TAG, correctEntity.correctedContent)
            }
            false -> {
                Log.d(TAG, correctEntity.errorMessage?: "error")
            }
        }
    }*/
}
