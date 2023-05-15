package com.musdev.papanote.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.musdev.papanote.R
import com.musdev.papanote.ui.theme.DarkDisableColor
import com.musdev.papanote.ui.theme.LightDisableColor
import com.musdev.papanote.ui.theme.pretendard

@Composable
fun GreetingScreen() {
    val darkTheme: Boolean = isSystemInDarkTheme()

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Text(
            text = "Improve your English through writing practice.",
            lineHeight = 38.sp,
            fontFamily = pretendard,
            style = TextStyle(
                fontSize = 32.sp,
                fontWeight = FontWeight.Thin,
                color = MaterialTheme.colorScheme.onPrimary
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "LingoNote",
            style = TextStyle(
                fontSize = 48.sp,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onPrimary
            ),
            fontFamily = pretendard

        )
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(16.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(if (darkTheme) DarkDisableColor else LightDisableColor)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.height(60.dp)
            ) {
                Icon(
                    painter = painterResource(
                        id = R.drawable.ic_baseline_auto_fix_high_24
                    ),
                    contentDescription = "Let's start",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(start = 15.dp),
                )
                Text(
                    modifier = Modifier.padding(14.dp),
                    text = "Learn better expressions with AI",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colorScheme.onPrimary
                    ),

                    fontFamily = pretendard
                )
            }
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(210.dp))
    }
}