package com.musdev.lingonote.presentation

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
import com.musdev.lingonote.R
import com.musdev.lingonote.ui.theme.DarkDisableColor
import com.musdev.lingonote.ui.theme.LightDisableColor
import com.musdev.lingonote.ui.theme.pretendard

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
            text = "꾸준한 작문으로\n당신의 영어를\n향상 시켜보세요.",
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
            text = "하루 영작",
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
                    text = "AI 교정으로 더 나은 표현을 익혀보세요.",
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