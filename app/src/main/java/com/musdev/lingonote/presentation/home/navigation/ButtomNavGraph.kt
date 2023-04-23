package com.musdev.lingonote.presentation.home.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.musdev.lingonote.presentation.achieve.AchieveScreen
import com.musdev.lingonote.presentation.notes.NotesScreen
import com.musdev.lingonote.presentation.notes.NotesViewModel

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    modifier: Modifier,
    viewModel: NotesViewModel
) {

    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            NotesScreen(modifier = modifier, viewModel = viewModel)
        }
        composable(route = BottomBarScreen.Achieve.route) {
            AchieveScreen()
        }
    }
}