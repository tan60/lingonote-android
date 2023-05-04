package com.musdev.lingonote.presentation.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.musdev.lingonote.core.domain.entities.NoteEntity
import com.musdev.lingonote.presentation.home.navigation.BottomBarScreen
import com.musdev.lingonote.ui.theme.pretendard

lateinit var navController: NavHostController
lateinit var onNoteClick: (noteEntity: NoteEntity) -> Unit
@Composable
fun NotesScreen(
    nav: NavHostController,
    modifier: Modifier,
    viewModel: NotesViewModel,
    onItemClick: (noteEntity: NoteEntity) -> Unit
) {
    navController = nav
    onNoteClick = onItemClick

    SideEffect {
        viewModel.fetchNotesAtFirst()
    }

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

@OptIn(ExperimentalMaterial3Api::class)
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
        onClick = {
            onNoteClick(noteEntity)
        }
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