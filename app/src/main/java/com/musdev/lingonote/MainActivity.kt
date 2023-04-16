package com.musdev.lingonote

import android.os.Bundle
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
import com.musdev.lingonote.ui.theme.LingoNoteTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val TAG = "AppDebug"
    private var name = "android"
    //@Inject private lateinit var aICorrectContentUseCase: AICorrectContentUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //DbService.build(this.application)

        val content = "Hi, this is DoHyoung Kim and I am Android developer. Am I doing now?"

        lifecycleScope.launch(Dispatchers.IO) {
            /*var aiCorrectEntity = aICorrectContentUseCase.correctMyContent(content)
            when (aiCorrectEntity.isSuccess) {
                true -> {
                    Log.d(TAG, aiCorrectEntity.correctedContent)
                }
                false -> {
                    Log.d(TAG, aiCorrectEntity.errorMessage)
                }
            }*/
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