package com.musdev.lingonote.presentation.home.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.musdev.lingonote.presentation.GreetingScreen
import com.musdev.lingonote.presentation.achieve.AchieveScreen
import com.musdev.lingonote.presentation.edit.EditScreen
import com.musdev.lingonote.presentation.edit.EditViewModel
import com.musdev.lingonote.presentation.notes.NotesScreen
import com.musdev.lingonote.presentation.notes.NotesViewModel

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    modifier: Modifier,
    noteViewModel: NotesViewModel,
    editViewModel: EditViewModel
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
            NotesScreen(modifier = modifier, viewModel = noteViewModel)
        }
        composable(route = BottomBarScreen.Achieve.route) {
            AchieveScreen()
        }
        composable(route = "edit") {
            EditScreen(modifier = modifier, editViewModel = editViewModel)
        }
        composable(route = "greeting") {
            GreetingScreen()
        }
    }
}