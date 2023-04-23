package com.musdev.lingonote.presentation.home.navigation

import androidx.annotation.DrawableRes
import com.musdev.lingonote.R

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    @DrawableRes val iconId: Int
) {
    object Home : BottomBarScreen(
        route = "home",
        title = "Home",
        iconId = R.drawable.ic_baseline_dynamic_feed_24
    )
    object Achieve : BottomBarScreen(
        route = "achieve",
        title = "Achievement",
        iconId = R.drawable.ic_baseline_auto_graph_24
    )
}
