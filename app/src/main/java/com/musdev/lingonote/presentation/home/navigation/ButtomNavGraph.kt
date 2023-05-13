package com.musdev.lingonote.presentation.home.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.musdev.lingonote.presentation.GreetingScreen
import com.musdev.lingonote.presentation.achieve.AchieveScreen
import com.musdev.lingonote.presentation.edit.EditScreen
import com.musdev.lingonote.presentation.home.coroutineScope
import com.musdev.lingonote.presentation.home.sharedAchieveViewModel
import com.musdev.lingonote.presentation.home.sharedEditViewModel
import com.musdev.lingonote.presentation.home.sharedNotesViewModel
import com.musdev.lingonote.presentation.home.sharedPreviewViewModel
import com.musdev.lingonote.presentation.home.showSnackBar
import com.musdev.lingonote.presentation.home.snackHostState
import com.musdev.lingonote.presentation.notes.NotesScreen
import com.musdev.lingonote.presentation.preview.PreviewScreen
import com.musdev.lingonote.presentation.settings.SettingScreen

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