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
import com.musdev.lingonote.core.data.model.GPTResponseModel
import com.musdev.lingonote.core.data.repository.Repository
import com.musdev.lingonote.core.data.repository.database.NoteDatabase
import com.musdev.lingonote.ui.theme.LingoNoteTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private var name = "android"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NoteDatabase.build(this.application)

        val content = "Hi, this is DoHyoung Kim and I am Android developer. Am I doing now?"

        lifecycleScope.launch(Dispatchers.IO) {
            val result = Repository.improveNoteContent(content)

            if (result.data is GPTResponseModel) {
                Log.d("TEST", (result.data as GPTResponseModel).choices[0].text)
            } else if (result.data is String){
                Log.d("TEST", result.data as String)
            }
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