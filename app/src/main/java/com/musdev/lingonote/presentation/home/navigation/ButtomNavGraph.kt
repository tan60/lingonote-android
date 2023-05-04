package com.musdev.lingonote.presentation.home.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.musdev.lingonote.presentation.GreetingScreen
import com.musdev.lingonote.presentation.achieve.AchieveScreen
import com.musdev.lingonote.presentation.edit.EditScreen
import com.musdev.lingonote.presentation.edit.EditViewModel
import com.musdev.lingonote.presentation.home.coroutineScope
import com.musdev.lingonote.presentation.home.showSnackBar
import com.musdev.lingonote.presentation.home.snackHostState
import com.musdev.lingonote.presentation.notes.NotesScreen
import com.musdev.lingonote.presentation.notes.NotesViewModel
import com.musdev.lingonote.presentation.preview.PreviewScreen
import com.musdev.lingonote.presentation.preview.PreviewViewModel

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    modifier: Modifier,
    notesViewModel: NotesViewModel,
    editViewModel: EditViewModel,
    previewViewModel: PreviewViewModel
) {
    var destination = if (notesViewModel.uiState.noteItems.isEmpty()) {
        BottomBarScreen.Greeting.route
    } else {
        BottomBarScreen.Notes.route
    }

    NavHost(
        navController = navController,
        startDestination = destination
    ) {
        composable(route = BottomBarScreen.Notes.route) {
            NotesScreen(modifier = modifier, nav = navController, viewModel = notesViewModel,
                onItemClick = { noteEntity ->
                    previewViewModel.setCurrentNote(noteEntity = noteEntity, enableDelete = true)
                    navController.navigate(BottomBarScreen.Preview.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(route = BottomBarScreen.Achieve.route) {
            AchieveScreen()
        }
        composable(route = BottomBarScreen.Edit.route) {
            EditScreen(modifier = modifier, editViewModel = editViewModel)
        }
        composable(route = BottomBarScreen.Preview.route) {
            PreviewScreen(modifier = modifier,
                previewViewModel = previewViewModel,
                onCloseClick = {
                    navController.navigate(BottomBarScreen.Notes.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                },
                onRemoteNoteClick = { noteEntity ->
                    notesViewModel.shouldUpdate(true)
                    showSnackBar(snackHostState = snackHostState, coroutineScope = coroutineScope, "Remove Note!")

                    navController.navigate(BottomBarScreen.Notes.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(route = BottomBarScreen.Greeting.route) {
            GreetingScreen()
        }
    }
}