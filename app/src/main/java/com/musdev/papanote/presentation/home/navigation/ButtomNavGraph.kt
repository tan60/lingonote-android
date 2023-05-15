package com.musdev.papanote.presentation.home.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.musdev.papanote.presentation.GreetingScreen
import com.musdev.papanote.presentation.achieve.AchieveScreen
import com.musdev.papanote.presentation.edit.EditScreen
import com.musdev.papanote.presentation.home.coroutineScope
import com.musdev.papanote.presentation.home.sharedAchieveViewModel
import com.musdev.papanote.presentation.home.sharedEditViewModel
import com.musdev.papanote.presentation.home.sharedNotesViewModel
import com.musdev.papanote.presentation.home.sharedPreviewViewModel
import com.musdev.papanote.presentation.home.showSnackBar
import com.musdev.papanote.presentation.home.snackHostState
import com.musdev.papanote.presentation.notes.NotesScreen
import com.musdev.papanote.presentation.preview.PreviewScreen
import com.musdev.papanote.presentation.settings.SettingScreen

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = if (sharedNotesViewModel.uiState.isNoteEmpty) {
            BottomBarScreen.Greeting.route
        } else {
            BottomBarScreen.Notes.route
        }
    ) {
        composable(route = BottomBarScreen.Notes.route) {
            NotesScreen(modifier = modifier, viewModel = sharedNotesViewModel,
                onItemClick = { noteEntity ->
                    sharedPreviewViewModel.setCurrentNote(noteEntity = noteEntity, enableDelete = true)
                    navController.navigate(BottomBarScreen.Preview.route)
                }
            )
        }
        composable(route = BottomBarScreen.Achieve.route) {
            AchieveScreen(modifier = modifier, viewModel = sharedAchieveViewModel)
        }
        composable(route = BottomBarScreen.Edit.route) {
            EditScreen(modifier = modifier, viewModel = sharedEditViewModel)
        }
        composable(route = BottomBarScreen.Preview.route) {
            PreviewScreen(modifier = modifier,
                onCloseClick = {
                    navController.popBackStack()
                },
                onRemoveNoteClick = {
                    sharedNotesViewModel.shouldUpdate(true)
                    sharedAchieveViewModel.shouldUpdate(true)
                    showSnackBar(snackHostState = snackHostState, coroutineScope = coroutineScope, "Remove Note!")
                    navController.popBackStack()
                }
            )
        }
        composable(route = BottomBarScreen.Greeting.route) {
            GreetingScreen()
        }
        composable(route = BottomBarScreen.Settings.route) {
            SettingScreen(modifier = modifier)
        }
    }
}