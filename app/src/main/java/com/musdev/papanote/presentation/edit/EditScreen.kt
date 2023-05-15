package com.musdev.papanote.presentation.edit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.musdev.papanote.ui.theme.pretendard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    modifier: Modifier,
    viewModel: EditViewModel
) {
    Box(modifier = Modifier.padding(top = 80.dp, start = 16.dp, end = 16.dp, bottom = 56.dp)) {
        Column(
        ) {
            OutlinedTextField(
                value = viewModel.uiState.topic,
                onValueChange = {
                    viewModel.setTopicText(it)
                },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = pretendard
                ),
                label = {
                    Text(
                        text = "Topic",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = pretendard
                        )
                    )
                },
                placeholder = {
                    Text(
                        text = "What is topic of your writing?",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = pretendard
                        )
                    )
                },
                maxLines = 3,
                singleLine = false,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = MaterialTheme.colorScheme.secondary,
                    containerColor = MaterialTheme.colorScheme.background,
                    focusedLabelColor = MaterialTheme.colorScheme.onSecondary,
                    focusedBorderColor = MaterialTheme.colorScheme.onSecondary
                )
            )
            Spacer(modifier = Modifier.fillMaxWidth().height(8.dp))
            OutlinedTextField(
                value = viewModel.uiState.content,
                onValueChange = {
                    viewModel.setContentText(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                textStyle = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = pretendard
                ),
                label = {
                    Text(
                        text = "Content",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = pretendard
                        )
                    )
                },
                placeholder = {
                    Text(
                        text = "Please write the content.",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = pretendard
                        )
                    )
                },
                singleLine = false,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = MaterialTheme.colorScheme.secondary,
                    containerColor = MaterialTheme.colorScheme.background,
                    focusedLabelColor = MaterialTheme.colorScheme.onSecondary,
                    focusedBorderColor = MaterialTheme.colorScheme.onSecondary
                )
            )
        }
    }
}