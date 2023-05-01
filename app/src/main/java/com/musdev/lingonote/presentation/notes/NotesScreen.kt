package com.musdev.lingonote.presentation.notes

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import com.musdev.lingonote.presentation.home.TAG
import com.musdev.lingonote.ui.theme.DarkDisableColor
import com.musdev.lingonote.ui.theme.LightDisableColor
import com.musdev.lingonote.ui.theme.pretendard



@Composable
fun NotesScreen(
    modifier: Modifier,
    viewModel: NotesViewModel
) {
    //viewModel.fetchNotesAtFirst()

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (viewModel.uiState.isFetchingNotes) {
            //display loading indicator
            Box(modifier.fillMaxSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        } else {
            NoteListSection(noteEntities = viewModel.uiState.noteItems)
        }
    /*else if (viewModel.uiState.noteItems.size > 0) {
            //display Note List
            NoteListSection(noteEntities = viewModel.uiState.noteItems)
        } else {
            //display Greeting
            GreetingSection()
        }*/
    }
}

@Composable
fun NoteListSection(noteEntities: List<NoteEntity>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            contentPadding = PaddingValues(start = 10.dp, end = 10.dp, bottom = 96.dp),
            //modifier = Modifier.fillMaxHeight().padding(bottom = 60.dp),
        ) {
            items(noteEntities.size) {
                NoteItem(noteEntity = noteEntities[it])
            }
        }
    }
}

@Composable
fun NoteItem(noteEntity: NoteEntity) {
    val customCardColors = CardDefaults.cardElevation(
        defaultElevation = 10.dp,
        pressedElevation = 2.dp,
        focusedElevation = 4.dp
    )
    Card(
        modifier = Modifier
            .padding(top = 16.dp),
        //.clip(RoundedCornerShape(16.dp))
        shape = CardDefaults.shape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
        ) {
            //topic
            Text(
                text = noteEntity.topic,
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
            //content
            Text(
                text = noteEntity.content,
                style = TextStyle(
                    fontSize = 21.sp,
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.onPrimary
                ),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                val correctedType =
                    if (noteEntity.correctedContent.isNotEmpty()) "AI Corrected" else ""
                //corrected type
                Text(
                    text = correctedType,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colorScheme.secondary
                    )
                )

                //issue date
                Text(
                    text = noteEntity.issueDate,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Light,
                        //color = MaterialTheme.colorScheme.onSecondary
                    )
                )
            }
        }
    }
}