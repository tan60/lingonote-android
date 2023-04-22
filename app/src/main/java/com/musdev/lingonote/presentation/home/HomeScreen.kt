package com.musdev.lingonote.presentation.home

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.musdev.lingonote.R

@Composable
fun HomeScreen() {
    GreetingSection()
}

@Composable
fun GreetingSection() {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(24.dp)
            .background(Color.Green)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Text(
            text = "꾸준한 작문으로\n당신의 영어를\n향상 시키세요.",
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = "하루 영작",
            style = TextStyle(
                fontSize = 50.sp,
                fontWeight = FontWeight(3)
            )
        )
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(16.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Red)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(
                        id = R.drawable.ic_baseline_edit_24
                    ),
                    contentDescription = "Play",
                    tint = Color.Black,
                    modifier = Modifier.padding(start = 15.dp),
                )
                Text(
                    modifier = Modifier.padding(15.dp),
                    text = "지금 시작해 볼까요?",
                    style = TextStyle(fontSize = 18.sp)
                )
            }
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(8.dp))
        Text(
            modifier = Modifier.align(Alignment.End),
            text = "AI 교정으로 더 나은 표현을 익혀보세요.",
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight(3)
            )
        )
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(150.dp))
    }
}