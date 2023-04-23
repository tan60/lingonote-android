package com.musdev.lingonote.presentation.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.musdev.lingonote.core.domain.entities.NoteEntity
import com.musdev.lingonote.core.domain.usecases.CorrectContentUseCase
import com.musdev.lingonote.core.domain.usecases.NoteUseCase
import com.musdev.lingonote.presentation.notes.NotesViewModel
import com.musdev.lingonote.ui.theme.LingoNoteTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    //test code
    @Inject
    lateinit var correctContentUseCase: CorrectContentUseCase
    @Inject
    lateinit var noteUseCase: NoteUseCase

    @Inject lateinit var viewModel: NotesViewModel


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //test code
        lifecycleScope.launch(Dispatchers.IO) {
            val startIndex = getTotalCountTest()

            for (i in startIndex.. startIndex + 3) {
                postNoteTest("test topic $i title title sadklfjlksajdflkjsalfkjlsakdjflkasjdfkl", "test content $i sdfksakldfjlksjadfkljsldkjfkj lksajdfkljsadflkjsalkdjkl ksldjafkljsdafkljsadlkfj ksjdflkjasdflkj")
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.fetchNotes()
            }
        }
        setContent {
            LingoNoteTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen(modifier = Modifier.fillMaxSize(), viewModel)
                }
            }
        }
    }

    private suspend fun getTotalCountTest(): Int {
        return noteUseCase.getTotalNoteCount()
    }
    private suspend fun postNoteTest(topic: String, content: String) {
        val noteEntity = NoteEntity().apply {
            this.topic = topic
            this.content = content
        }

        noteUseCase.postNote(noteEntity)
    }
}
