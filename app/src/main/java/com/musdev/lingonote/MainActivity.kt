package com.musdev.lingonote

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.musdev.lingonote.core.domain.entities.NoteEntity
import com.musdev.lingonote.core.domain.usecases.CorrectContentUseCase
import com.musdev.lingonote.core.domain.usecases.NoteUseCase
import com.musdev.lingonote.ui.theme.LingoNoteTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val TAG = "AppDebug"
    private var name = "android"

    @Inject
    lateinit var correctContentUseCase: CorrectContentUseCase

    @Inject
    lateinit var noteUseCase: NoteUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val content = "Hi, this is DoHyoung Kim and I am Android developer. Am I doing now?"

        lifecycleScope.launch(Dispatchers.IO) {
            //correctContentTest(content)

            val startIndex = getTotalCountTest()

            for (i in startIndex.. startIndex + 3) {
                postNoteTest("test topic $i", "test content $i")
            }

            fetchNotesTest()
            updateNoteTest(1, "update topic 0", "update content 0")
            getFirstNoteTest()

        }

        setContent {
            LingoNoteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(name)
                }
            }
        }
    }

    private suspend fun correctContentTest(content: String) {
        var correctEntity = correctContentUseCase.correctMyContent(content)
        when (correctEntity.isSuccess) {
            true -> {
                Log.d(TAG, correctEntity.correctedContent)
            }
            false -> {
                Log.d(TAG, correctEntity.errorMessage)
            }
        }
    }

    private suspend fun postNoteTest(topic: String, content: String) {
        val noteEntity = NoteEntity().apply {
            this.topic = topic
            this.content = content
        }

        noteUseCase.postNote(noteEntity)
    }

    private suspend fun updateNoteTest(postNo: Int, topic: String, content: String) {
        val noteEntity = NoteEntity().apply {
            this.postNo = postNo
            this.topic = topic
            this.content = content
        }

        noteUseCase.updateNote(noteEntity)
    }

    private suspend fun getTotalCountTest(): Int {
        return noteUseCase.getTotalNoteCount()
    }
    private suspend fun getFirstNoteTest() {
        Log.d(TAG, "First Note: ${noteUseCase.getFirstNote()}")
    }

    private suspend fun fetchNotesTest() {
        val noteEntities = noteUseCase.fetchNotes()

        for (i in noteEntities.indices) {
            Log.d(TAG, noteEntities[i].toString())
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LingoNoteTheme {
        Greeting("Android")
    }
}