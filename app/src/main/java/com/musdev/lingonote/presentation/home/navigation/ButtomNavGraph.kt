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
import com.musdev.lingonote.presentation.notes.NotesScreen
import com.musdev.lingonote.presentation.notes.NotesViewModel
import com.musdev.lingonote.presentation.preview.PreviewScreen
import com.musdev.lingonote.presentation.preview.PreviewViewModel

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    modifier: Modifier,
    noteViewModel: NotesViewModel,
    editViewModel: EditViewModel,
    previewViewModel: PreviewViewModel
) {
    var destination = if (noteViewModel.uiState.noteItems.isEmpty()) {
        BottomBarScreen.Greeting.route
    } else {
        BottomBarScreen.Notes.route
    }

    NavHost(
        navController = navController,
        startDestination = destination
    ) {
        composable(route = BottomBarScreen.Notes.route) {
            NotesScreen(modifier = modifier, nav = navController, viewModel = noteViewModel,
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
            PreviewScreen(modifier = modifier, previewViewModel = previewViewModel)
        }
        composable(route = "greeting") {
            GreetingScreen()
        }
    }
}