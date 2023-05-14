package com.musdev.lingonote.presentation.achieve

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.musdev.lingonote.R
import com.musdev.lingonote.core.domain.entities.AchieveEntity
import com.musdev.lingonote.presentation.home.navigation.BottomBarScreen
import com.musdev.lingonote.presentation.home.sharedNavHostController
import com.musdev.lingonote.ui.theme.DarkDisableColor
import com.musdev.lingonote.ui.theme.LightDisableColor
import com.musdev.lingonote.ui.theme.pretendard

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

    Box(modifier = Modifier.padding(start = 32.dp, top = 32.dp, end = 32.dp)) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
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
                        restoreState = true
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
            Spacer(modifier = Modifier.height(20.dp))
            val noteString = if (viewModel.uiState.totalNotesCount > 1) "notes" else "note"
            val dayString = if (viewModel.uiState.totalDaysCount > 1) "days" else "day"

            Text(
                text = "\"You created ${viewModel.uiState.totalNotesCount} $noteString in ${viewModel.uiState.totalDaysCount} $dayString\".",
                style = TextStyle(
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Light,
                    fontFamily = pretendard,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.padding(bottom = 0.dp)) {
                Row(modifier = Modifier.height(334.dp)) {//box height * 7 + 12(padding 2*16)
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

            BuildAdView()
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
            .size(46.dp)
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

@Composable
fun BuildAdView() {
    val adId = stringResource(id = R.string.sample_banner_id)
    val adRequest = AdRequest.Builder().build()

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            AdView(it).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = adId
                loadAd(adRequest)
            }
        },
        update = {
            it.loadAd(adRequest)
        }
    )
}