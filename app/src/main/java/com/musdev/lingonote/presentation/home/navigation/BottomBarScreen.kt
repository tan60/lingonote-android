package com.musdev.lingonote.presentation.home.navigation

import androidx.annotation.DrawableRes
import com.musdev.lingonote.R

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    @DrawableRes val iconId: Int
) {
    //singleton
    object Notes : BottomBarScreen(
        route = "notes",
        title = "Notes",
        iconId = R.drawable.ic_baseline_dynamic_feed_24
    )
    object Achieve : BottomBarScreen(
        route = "achieve",
        title = "Achievement",
        iconId = R.drawable.ic_baseline_auto_graph_24
    )

    object Edit: BottomBarScreen(
        route = "edit",
        title = "Edit",
        iconId = R.drawable.ic_baseline_add_24
    )
    object Greeting: BottomBarScreen(
        route = "greeting",
        title = "Greeting",
        iconId = R.drawable.ic_baseline_add_24
    )

    object Preview: BottomBarScreen(
        route = "preview",
        title = "Preview",
        iconId = R.drawable.ic_baseline_close_24
    )
}
