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
import com.musdev.lingonote.presentation.home.sharedEditViewModel
import com.musdev.lingonote.presentation.home.sharedNotesViewModel
import com.musdev.lingonote.presentation.home.sharedPreviewViewModel
import com.musdev.lingonote.presentation.home.showSnackBar
import com.musdev.lingonote.presentation.home.snackHostState
import com.musdev.lingonote.presentation.notes.NotesScreen
import com.musdev.lingonote.presentation.notes.NotesViewModel
import com.musdev.lingonote.presentation.preview.PreviewScreen
import com.musdev.lingonote.presentation.preview.PreviewViewModel

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    modifier: Modifier
) {
    var destination = if (sharedNotesViewModel.uiState.noteItems.isEmpty()) {
        BottomBarScreen.Greeting.route
    } else {
        BottomBarScreen.Notes.route
    }

    NavHost(
        navController = navController,
        startDestination = destination
    ) {
        composable(route = BottomBarScreen.Notes.route) {
            NotesScreen(modifier = modifier, viewModel = sharedNotesViewModel,
                onItemClick = { noteEntity ->
                    sharedPreviewViewModel.setCurrentNote(noteEntity = noteEntity, enableDelete = true)
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
            EditScreen(modifier = modifier, viewModel = sharedEditViewModel)
        }
        composable(route = BottomBarScreen.Preview.route) {
            PreviewScreen(modifier = modifier,
                viewViewModel = sharedPreviewViewModel,
                onCloseClick = {
                    navController.navigate(BottomBarScreen.Notes.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                },
                onRemoteNoteClick = { noteEntity ->
                    sharedNotesViewModel.shouldUpdate(true)
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