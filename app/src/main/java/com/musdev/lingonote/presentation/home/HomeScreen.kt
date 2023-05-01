package com.musdev.lingonote.presentation.home

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.musdev.lingonote.R
import com.musdev.lingonote.presentation.edit.EditViewModel
import com.musdev.lingonote.presentation.home.navigation.BottomBarScreen
import com.musdev.lingonote.presentation.home.navigation.BottomNavGraph
import com.musdev.lingonote.presentation.notes.NotesViewModel
import com.musdev.lingonote.ui.theme.pretendard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

lateinit var snackHostState: SnackbarHostState
lateinit var coroutineScope: CoroutineScope
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier,
    noteViewModel: NotesViewModel,
    editViewModel: EditViewModel
) {
    val navController = rememberNavController()
    snackHostState = remember { SnackbarHostState() }
    coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackHostState)
        },
        topBar = {
            buildTopBar(navController = navController)
        },
        floatingActionButton = {
            Row() {
                when (getCurrentRoute(navController = navController)) {
                    BottomBarScreen.Notes.route -> {
                        buildNotesScreenActionButton(navController = navController)
                    }
                    BottomBarScreen.Edit.route -> {
                        buildEditScreenFloatActionButton(
                            navController = navController,
                            editViewModel = editViewModel
                        )
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = {
            buildBottomBar(navController = navController)
        }

    ) { contentPadding ->
        BottomNavGraph(
            navController = navController,
            modifier = modifier,
            noteViewModel = noteViewModel,
            editViewModel = editViewModel
        )
    }
}

@Composable
fun getCurrentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
@Composable
fun buildTopBar(navController: NavHostController) {
    Spacer(modifier =Modifier.height(16.dp))
    when (getCurrentRoute(navController = navController)) {
        BottomBarScreen.Edit.route -> {
            Box(modifier = Modifier
                .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Let's write in English",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = pretendard,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    )

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(48.dp)
                    ) {
                        IconButton(
                            onClick = {
                                navController.navigate(BottomBarScreen.Notes.route) {
                                    popUpTo(navController.graph.findStartDestination().id)
                                    launchSingleTop = true
                                }
                            },
                            modifier = Modifier.size(36.dp)
                        )
                        {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_close_24),
                                contentDescription = "new note",
                                tint = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun buildNotesScreenActionButton(navController: NavHostController) {
    FloatingActionButton(
        containerColor = MaterialTheme.colorScheme.secondary,
        onClick = {
            navController.navigate(BottomBarScreen.Edit.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp),

            ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_add_24),
                contentDescription = "new note",
                tint = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("New Note")
        }
    }
}

@Composable
fun buildEditScreenFloatActionButton(
    navController: NavHostController,
    editViewModel: EditViewModel
) {
    FloatingActionButton(
        containerColor = if (editViewModel.uiState.isPreviewEnable)
            MaterialTheme.colorScheme.secondary
        else MaterialTheme.colorScheme.tertiary,

        onClick = {
            if (editViewModel.uiState.isPreviewEnable) {
                //go to preview screen
            }
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_remove_red_eye_24),
                contentDescription = "preview",
                tint = if (editViewModel.uiState.isPreviewEnable)
                    MaterialTheme.colorScheme.onSecondary
                else MaterialTheme.colorScheme.onTertiary,
            )
        }
    }
    Spacer(modifier = Modifier.width(8.dp))
    FloatingActionButton(
        containerColor = if (editViewModel.uiState.isPreviewEnable)
            MaterialTheme.colorScheme.secondary
        else MaterialTheme.colorScheme.tertiary,

        onClick = {
            if (editViewModel.uiState.isSaveEnable) {
                editViewModel.postNewNote()
                showSnackBar(snackHostState = snackHostState, coroutineScope, "New note is created!")

                navController.navigate(BottomBarScreen.Notes.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_check_24),
                contentDescription = "save",
                tint = if (editViewModel.uiState.isPreviewEnable)
                    MaterialTheme.colorScheme.onSecondary
                else MaterialTheme.colorScheme.onTertiary,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Save")
        }
    }
}

@Composable
fun buildBottomBar(navController: NavHostController) {
    when (getCurrentRoute(navController = navController)) {
        BottomBarScreen.Notes.route, BottomBarScreen.Achieve.route -> {
            BottomAppBar(
                modifier = Modifier
                    .height(65.dp),

                ) {
                BottomBar(navController = navController)
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavController) {
    val screens = listOf(
        BottomBarScreen.Notes,
        BottomBarScreen.Achieve
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Row(
        modifier = Modifier
            //.padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 8.dp)
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        screens.forEach { screen ->
            AddBottomBarItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddBottomBarItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavController
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
    val background =
        if (selected) MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f) else Color.Transparent
    val contentColor =
        if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary

    Box(
        modifier = Modifier
            .padding(10.dp)
            .height(40.dp)
            .clip(CircleShape)
            .background(background)
            .clickable(onClick = {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            })
    ) {
        Row(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(id = screen.iconId/*if (selected) screen.icon_focused else screen.icon*/),
                contentDescription = "icon",
                tint = contentColor
            )
            AnimatedVisibility(visible = selected) {
                Text(
                    text = screen.title,
                    color = contentColor
                )
            }
        }
    }
}


fun showSnackBar(snackHostState: SnackbarHostState,
                         coroutineScope: CoroutineScope,
                         message: String) {

    coroutineScope.launch {
        snackHostState.showSnackbar(message)
    }
}

