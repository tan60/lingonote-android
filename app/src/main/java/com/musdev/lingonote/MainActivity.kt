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
import com.musdev.lingonote.core.data.model.GPTError
import com.musdev.lingonote.core.data.model.GPTResponseModel
import com.musdev.lingonote.core.data.repository.Repository
import com.musdev.lingonote.core.data.services.DbService
import com.musdev.lingonote.ui.theme.LingoNoteTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private var name = "android"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DbService.build(this.application)

        val content = "Hi, this is DoHyoung Kim and I am Android developer. Am I doing now?"



        lifecycleScope.launch(Dispatchers.IO) {

            //Domain layer에서 처리해야 하는데 테스트로 여기서 처리함
            val result = Repository.improveNoteContent(content)

            when (result.status) {
                200 -> {
                    Log.d("TEST", (result.data as GPTResponseModel).choices[0].text)
                }
                else -> {
                    if (result.data is GPTError) {
                        Log.d("TEST", (result.data as GPTError).error.message)
                    } else if (result.data is String){
                        Log.d("TEST", result.data as String)
                    }
                }
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