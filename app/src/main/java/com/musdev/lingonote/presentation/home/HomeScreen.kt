package com.musdev.lingonote.presentation.home

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
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
import com.musdev.lingonote.presentation.preview.PreviewViewModel
import com.musdev.lingonote.ui.theme.DarkDisableColor
import com.musdev.lingonote.ui.theme.LightDisableColor
import com.musdev.lingonote.ui.theme.pretendard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

lateinit var snackHostState: SnackbarHostState
lateinit var coroutineScope: CoroutineScope
lateinit var sharedNavHostController: NavHostController

lateinit var sharedNotesViewModel: NotesViewModel
lateinit var sharedEditViewModel: EditViewModel
lateinit var sharedPreviewViewModel: PreviewViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier,
    notesViewModel: NotesViewModel,
    editViewModel: EditViewModel,
    previewViewModel: PreviewViewModel
) {

    sharedNotesViewModel = notesViewModel
    sharedEditViewModel = editViewModel
    sharedPreviewViewModel = previewViewModel

    sharedNavHostController = rememberNavController()
    snackHostState = remember { SnackbarHostState() }
    coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackHostState)
        },
        topBar = {
            buildTopBar(navController = sharedNavHostController)
        },
        floatingActionButton = {
            Row() {
                when (getCurrentRoute(navController = sharedNavHostController)) {
                    BottomBarScreen.Notes.route, BottomBarScreen.Greeting.route -> {
                        buildNotesScreenActionButton(navController = sharedNavHostController)
                    }
                    BottomBarScreen.Edit.route -> {
                        buildEditScreenFloatActionButton()
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = {
            buildBottomBar(navController = sharedNavHostController)
        }

    ) { contentPadding ->
        BottomNavGraph(
            navController = sharedNavHostController,
            modifier = modifier
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
                                var destination = if (sharedNotesViewModel.uiState.noteItems.isEmpty()) {
                                    BottomBarScreen.Greeting.route
                                } else {
                                    BottomBarScreen.Notes.route
                                }
                                sharedEditViewModel.initUiState()
                                navController.popBackStack()
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
                //popUpTo(navController.graph.findStartDestination().id)
                //launchSingleTop = true
                restoreState = true
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
) {
    FloatingActionButton(
        containerColor = if (sharedEditViewModel.uiState.isPreviewEnable)
            MaterialTheme.colorScheme.secondary
        else MaterialTheme.colorScheme.tertiary,

        onClick = {
            if (sharedEditViewModel.uiState.isPreviewEnable) {
                sharedPreviewViewModel.setCurrentNote(noteEntity = sharedEditViewModel.buildNoteForPreview(), enableDelete = false)
                sharedNavHostController.navigate(BottomBarScreen.Preview.route)
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
                tint = if (sharedEditViewModel.uiState.isPreviewEnable)
                    MaterialTheme.colorScheme.onSecondary
                else MaterialTheme.colorScheme.onTertiary,
            )
        }
    }
    Spacer(modifier = Modifier.width(8.dp))
    FloatingActionButton(
        containerColor = if (sharedEditViewModel.uiState.isPreviewEnable)
            MaterialTheme.colorScheme.secondary
        else MaterialTheme.colorScheme.tertiary,

        onClick = {
            if (sharedEditViewModel.uiState.isSaveEnable) {
                coroutineScope.launch {
                    sharedEditViewModel.postNewNote { result ->
                        sharedNotesViewModel.shouldUpdate(result)
                        sharedEditViewModel.initUiState()

                        showSnackBar(snackHostState = snackHostState, coroutineScope = coroutineScope, "New note is created!")
                    }
                }

                sharedNavHostController.popBackStack()
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
                tint = if (sharedEditViewModel.uiState.isPreviewEnable)
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
                modifier = Modifier.height(65.dp),
                containerColor = MaterialTheme.colorScheme.background,
                tonalElevation = 3.dp
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
    val darkTheme: Boolean = isSystemInDarkTheme()

    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
    val background =
        if (selected) {
            if (darkTheme) DarkDisableColor else LightDisableColor
        } else Color.Transparent
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
        snackHostState.showSnackbar(message = message)
    }
}

