package com.musdev.papanote.presentation.achieve

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.musdev.lingonote.R
import com.musdev.lingonote.core.domain.entities.AchieveEntity
import com.musdev.lingonote.presentation.home.navigation.BottomBarScreen
import com.musdev.lingonote.presentation.home.sharedNavHostController
import com.musdev.lingonote.presentation.home.sharedPreviewViewModel
import com.musdev.lingonote.presentation.notes.NoteItem
import com.musdev.lingonote.ui.theme.DarkDisableColor
import com.musdev.lingonote.ui.theme.LightDisableColor
import com.musdev.lingonote.ui.theme.pretendard
import java.util.Random

@Composable
fun AchieveScreen(
    modifier: Modifier,
    viewModel: AchieveViewModel
) {
    SideEffect {
        viewModel.fetchAchieveAtFirst()
    }

    var scrollState = rememberLazyGridState()

    LaunchedEffect(key1 = "scroll", block = {
        scrollState.animateScrollToItem(scrollState.layoutInfo.totalItemsCount)
    })

    Box(modifier = Modifier.padding(32.dp)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),

                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Your\nAchievement",
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = pretendard,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )
                IconButton(onClick = {
                    sharedNavHostController.navigate(BottomBarScreen.Settings.route) {
                        popUpTo(sharedNavHostController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(R.drawable.ic_baseline_settings_24),
                        contentDescription = "setting",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            val noteString = if (viewModel.uiState.totalNotesCount > 1) "notes" else "note"
            val dayString = if (viewModel.uiState.totalDaysCount > 1) "days" else "day"
            Text(
                modifier = Modifier.align(Alignment.End),
                textAlign = TextAlign.Center,
                text = "\"You created ${viewModel.uiState.totalNotesCount} $noteString in ${viewModel.uiState.totalDaysCount} $dayString\".",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Light,
                    fontFamily = pretendard,
                    color = MaterialTheme.colorScheme.onPrimary,
                    lineHeight = 32.sp,
                )
            )
            Box(modifier = Modifier.padding(bottom = 48.dp)) {
                Row(modifier = Modifier.height(336.dp)) {
                    LazyHorizontalGrid(
                        rows = GridCells.Fixed(7),
                        state = scrollState
                    ) {
                        items(viewModel.uiState.calendarItems.size) {
                            buildAchieveItem(viewModel.uiState.calendarItems[it])
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun buildAchieveItem(item: Pair<String, AchieveEntity?>) {
    val darkTheme: Boolean = isSystemInDarkTheme()

    var color: Color = Color.Transparent
    var text = ""

    item.second?.let {
        if (it is AchieveEntity) {
            color = Color(0x57C5B6).copy(alpha = (if (it.postedCount > 3) 3 else it.postedCount) * 0.3333f)
            text = it.date
        }
    }

    Box(
        modifier = Modifier
            .size(48.dp)
            .padding(2.dp),
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(if (darkTheme) DarkDisableColor else LightDisableColor)
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(color)
        ) {
            Text("$text", style = TextStyle(fontSize = 12.sp))
        }
    }
}