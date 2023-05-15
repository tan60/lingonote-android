package com.musdev.papanote.presentation.preview

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.musdev.papanote.R
import com.musdev.papanote.core.domain.entities.NoteEntity
import com.musdev.papanote.presentation.home.navigation.BottomBarScreen
import com.musdev.papanote.presentation.home.sharedNavHostController
import com.musdev.papanote.presentation.home.sharedPreviewViewModel
import com.musdev.papanote.ui.theme.DarkDisableColor
import com.musdev.papanote.ui.theme.LightDisableColor
import com.musdev.papanote.ui.theme.pretendard

@Composable
fun PreviewScreen(
    modifier: Modifier,
    onCloseClick: () -> Unit,
    onRemoveNoteClick: (noteEntity: NoteEntity) -> Unit
) {
    var openAIKey by remember {
        mutableStateOf(sharedPreviewViewModel.getOpenAIKey())
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp, bottom = 66.dp)
                .align(Alignment.TopStart),
            shape = CardDefaults.shape,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                        .align(Alignment.TopStart),
                ) {
                    //topic
                    Text(
                        text = sharedPreviewViewModel.currentNote.topic,
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = pretendard,
                            color = MaterialTheme.colorScheme.onPrimary
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row() {
                            if (sharedPreviewViewModel.uiState.enableDelete) {
                                AddPreviewModeItem(previewMode = PreviewMode.ORIGINAL, currentPreviewMode = sharedPreviewViewModel.uiState.previewMode)
                                Spacer(modifier = Modifier.width(5.dp))
                                AddPreviewModeItem(
                                    previewMode = PreviewMode.CORRECTED,
                                    currentPreviewMode = sharedPreviewViewModel.uiState.previewMode
                                )
                            }

                        }
                        //issue date
                        Text(
                            text = if (sharedPreviewViewModel.uiState.enableDelete) sharedPreviewViewModel.currentNote.issueDate else "Preview",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = pretendard,
                                fontWeight = FontWeight.Light,
                                //color = MaterialTheme.colorScheme.onSecondary
                            )
                        )
                    }

                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp))

                    //content
                    when (sharedPreviewViewModel.uiState.previewMode) {
                        PreviewMode.ORIGINAL -> {
                            Text(
                                modifier = Modifier
                                    .padding(bottom = 24.dp)
                                    .verticalScroll(rememberScrollState()),
                                text = sharedPreviewViewModel.currentNote.content,
                                style = TextStyle(
                                    fontSize = 21.sp,
                                    fontFamily = pretendard,
                                    fontWeight = FontWeight.Light,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        }
                        PreviewMode.CORRECTED -> {
                            when(openAIKey.isNotEmpty()) { //openAI KEY가 있는 상태
                                true -> {
                                    when(sharedPreviewViewModel.currentNote.correctedContent.isEmpty()) {
                                        true -> { //AI 교정 내용이 없는 상태 --> AI 교정 요청 상태를 표시해야 함
                                            when (sharedPreviewViewModel.uiState.correctState) {
                                                RequestState.REQUEST -> {
                                                    Box(modifier.fillMaxSize()) {
                                                        CircularProgressIndicator(
                                                            color = MaterialTheme.colorScheme.onPrimary,
                                                            modifier = Modifier.align(Alignment.Center)
                                                        )
                                                    }
                                                }
                                                RequestState.ERROR -> {
                                                    Column(
                                                        horizontalAlignment = Alignment.CenterHorizontally,
                                                        modifier = modifier
                                                            .wrapContentHeight()
                                                    ) {
                                                        Text(
                                                            text = "오류 : \n${sharedPreviewViewModel.uiState.correctedError}",
                                                            style = TextStyle(
                                                                fontSize = 21.sp,
                                                                fontFamily = pretendard,
                                                                fontWeight = FontWeight.Light,
                                                                color = MaterialTheme.colorScheme.onPrimary
                                                            )
                                                        )
                                                        Spacer(modifier = Modifier.height(16.dp))
                                                        TextButton(
                                                            modifier = Modifier.align(Alignment.End),
                                                            onClick = {
                                                                sharedPreviewViewModel.initUiState()
                                                                sharedPreviewViewModel.correctAI(sharedPreviewViewModel.currentNote.content)
                                                            },
                                                            colors = ButtonDefaults.buttonColors(
                                                                containerColor =  MaterialTheme.colorScheme.onPrimary,
                                                                contentColor = MaterialTheme.colorScheme.primary
                                                            ),
                                                            shape = RoundedCornerShape(6.dp)
                                                        ) {
                                                            Text(
                                                                text = "Retry",
                                                                style = TextStyle(
                                                                    fontSize = 16.sp,
                                                                    fontFamily = pretendard,
                                                                    color = MaterialTheme.colorScheme.primary
                                                                )
                                                            )
                                                        }
                                                    }
                                                }
                                                else -> {
                                                    //do nothing
                                                }
                                            }
                                        }
                                        false -> { //이미 AI 교정 된 내용이 있는 상태
                                            Text(
                                                modifier = Modifier
                                                    .padding(bottom = 24.dp)
                                                    .verticalScroll(rememberScrollState()),
                                                text = sharedPreviewViewModel.currentNote.correctedContent,
                                                style = TextStyle(
                                                    fontSize = 21.sp,
                                                    fontFamily = pretendard,
                                                    fontWeight = FontWeight.Light,
                                                    color = MaterialTheme.colorScheme.onPrimary
                                                )
                                            )
                                        }
                                    }

                                }
                                false -> { //openAI 키가 없는 상태
                                    Column(
                                        modifier.fillMaxWidth()
                                    ) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = "To use AI Improve, registration of an OpenAI key is required before use.",
                                            style = TextStyle(
                                                fontSize = 21.sp,
                                                fontWeight = FontWeight.Normal,
                                                fontFamily = pretendard,
                                                color = MaterialTheme.colorScheme.error
                                            )
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Button(
                                            onClick = {
                                                sharedNavHostController.navigate(BottomBarScreen.Settings.route) {
                                                    restoreState = true
                                                }
                                                sharedPreviewViewModel.setPreviewMode(previewMode = PreviewMode.ORIGINAL)
                                            },
                                            modifier = Modifier
                                                .wrapContentSize()
                                                .align(Alignment.End),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = MaterialTheme.colorScheme.secondary,
                                                contentColor = MaterialTheme.colorScheme.onSecondary,
                                            )
                                        ) {
                                            Text("Go to Settings", style = TextStyle(fontSize = 14.sp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(6.dp)
                        .align(Alignment.BottomStart)
                ) {
                    when (sharedPreviewViewModel.uiState.deleteState) {
                        RequestState.IDLE, RequestState.ERROR -> {
                            IconButton(onClick = {
                                if (sharedPreviewViewModel.uiState.enableDelete) {
                                    sharedPreviewViewModel.removeNote()
                                }
                            }) {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(R.drawable.ic_baseline_delete_outline_24),
                                    contentDescription = "delete",
                                    tint = if (sharedPreviewViewModel.uiState.enableDelete)
                                        MaterialTheme.colorScheme.onPrimary
                                    else MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        RequestState.REQUEST -> {
                            Box(modifier = Modifier.padding(12.dp)) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(18.dp),
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    strokeWidth = 3.dp
                                )
                            }
                        }
                        RequestState.DONE -> {
                            IconButton(onClick = {
                                //do nothing, this screen will be closed
                            }) {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(R.drawable.ic_baseline_delete_outline_24),
                                    contentDescription = "delete",
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                            sharedPreviewViewModel.initUiState()
                            onRemoveNoteClick(sharedPreviewViewModel.currentNote)
                        }
                    }
                    IconButton(onClick = {
                        sharedPreviewViewModel.initPreviewMode()
                        sharedPreviewViewModel.initUiState()
                        onCloseClick()
                    }) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(R.drawable.ic_baseline_keyboard_arrow_down_24),
                            contentDescription = "close",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
        Box(modifier = Modifier
            .align(Alignment.BottomStart)
            .fillMaxWidth()
            .height(50.dp)) {
            BuildAdView()
        }
    }
}

@Composable
fun RowScope.AddPreviewModeItem(
    previewMode: PreviewMode,
    currentPreviewMode: PreviewMode
) {
    val darkTheme: Boolean = isSystemInDarkTheme()

    val selected = previewMode == currentPreviewMode
    val background =
        if (selected) {
            if (darkTheme) DarkDisableColor else LightDisableColor
        } else Color.Transparent
    val contentColor =
        if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(background)
            .clickable(onClick = {
                sharedPreviewViewModel.setPreviewMode(previewMode = previewMode)

                if (previewMode == PreviewMode.CORRECTED
                    && sharedPreviewViewModel.currentNote.correctedContent.isEmpty()
                ) {
                    sharedPreviewViewModel.correctAI(sharedPreviewViewModel.currentNote.content)
                }
            })
    ) {
        Row(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AnimatedVisibility(visible = true) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (previewMode == PreviewMode.CORRECTED) {
                        Icon(
                            painter = painterResource(
                                id = R.drawable.ic_baseline_auto_fix_high_24
                            ),
                            contentDescription = "AI",
                            tint = contentColor,
                            modifier = Modifier.size(13.dp),
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                    }

                    Text(
                        text = if (previewMode == PreviewMode.ORIGINAL) "Mine" else "AI Improve",
                        color = contentColor,
                        style = TextStyle(fontSize = 14.sp, fontFamily = pretendard, fontWeight = FontWeight.Light)
                    )
                }
            }
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
