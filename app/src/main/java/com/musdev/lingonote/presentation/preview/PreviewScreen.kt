package com.musdev.lingonote.presentation.preview

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.musdev.lingonote.R
import com.musdev.lingonote.core.domain.entities.NoteEntity
import com.musdev.lingonote.ui.theme.DarkDisableColor
import com.musdev.lingonote.ui.theme.LightDisableColor
import com.musdev.lingonote.ui.theme.pretendard

@Composable
fun PreviewScreen(
    modifier: Modifier,
    previewViewModel: PreviewViewModel,
    onCloseClick: () -> Unit,
    onRemoteNoteClick: (noteEntity: NoteEntity) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp, bottom = 16.dp),
            shape = CardDefaults.shape,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                        .align(Alignment.TopStart),
                ) {
                    //topic
                    Text(
                        text = previewViewModel.currentNote.topic,
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = pretendard,
                            color = MaterialTheme.colorScheme.onPrimary
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row() {
                            AddPreviewMode(previewMode = PreviewMode.ORIGINAL, currentPreviewMode = previewViewModel.uiState.previewMode)
                            Spacer(modifier = Modifier.width(5.dp))
                            AddPreviewMode(previewMode = PreviewMode.CORRECTED, currentPreviewMode = previewViewModel.uiState.previewMode)
                        }
                        //issue date
                        Text(
                            text = previewViewModel.currentNote.issueDate,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = pretendard,
                                fontWeight = FontWeight.Light,
                                //color = MaterialTheme.colorScheme.onSecondary
                            )
                        )
                    }
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp))
                    //content
                    Text(
                        modifier = Modifier
                            .padding(bottom = 24.dp)
                            .verticalScroll(rememberScrollState()),
                        text = previewViewModel.currentNote.content,
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Light,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(6.dp)
                        .align(Alignment.BottomStart)
                ) {
                    when (previewViewModel.uiState.deleteState) {
                        RequestState.IDLE, RequestState.ERROR -> {
                            IconButton(onClick = {
                                previewViewModel.removeNote()
                            }) {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(R.drawable.ic_baseline_delete_outline_24),
                                    contentDescription = "delete",
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                        RequestState.REQUEST -> {
                            Box(modifier = Modifier.padding(12.dp)) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(18.dp),
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    strokeWidth = 3.dp
                                )
                            }
                        }
                        RequestState.DONE -> {
                            IconButton(onClick = {
                                //do nothing, this screen will be closed
                            }) {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(R.drawable.ic_baseline_delete_outline_24),
                                    contentDescription = "delete",
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                            previewViewModel.initUiState()
                            onRemoteNoteClick(previewViewModel.currentNote)
                        }
                    }

                    IconButton(onClick = {
                        previewViewModel.initUiState()
                        onCloseClick()
                    }) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(R.drawable.ic_baseline_keyboard_arrow_down_24),
                            contentDescription = "close",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RowScope.AddPreviewMode(
    previewMode: PreviewMode,
    currentPreviewMode: PreviewMode
) {
    val darkTheme: Boolean = isSystemInDarkTheme()

    val selected = previewMode == currentPreviewMode
    val background =
        if (selected) {
            if (darkTheme) DarkDisableColor else LightDisableColor
        } else Color.Transparent
    val contentColor =
        if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(background)
            .clickable(onClick = {

            })
    ) {
        Row(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AnimatedVisibility(visible = true) {
                Text(
                    text = if (previewMode == PreviewMode.ORIGINAL) "Mine" else "AI Improve",
                    color = contentColor,
                    style = TextStyle(fontSize = 14.sp, fontFamily = pretendard, fontWeight = FontWeight.Light)
                )
            }
        }
    }
}
