package com.musdev.lingonote.presentation.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.musdev.lingonote.R
import com.musdev.lingonote.presentation.home.sharedNavHostController
import com.musdev.lingonote.presentation.home.sharedSettingViewModel
import com.musdev.lingonote.ui.theme.pretendard

@Composable
fun SettingScreen(
    modifier: Modifier
) {
    val showOpenAIKeyDialog = remember { mutableStateOf(false) }
    val showOpenInstructionDialog = remember { mutableStateOf(false) }

    Box(modifier = Modifier.padding(32.dp)) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            val context = LocalContext.current

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),

                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Settings",
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = pretendard,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )
                IconButton(onClick = {
                    sharedNavHostController.popBackStack()
                }) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(R.drawable.ic_baseline_close_24),
                        contentDescription = "close",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            //set OpenAI
            Card(
                shape = CardDefaults.shape,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                ) {
                    Text(
                        text = "OpenAI",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = pretendard,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    buildSettingItem("Set API Key", onClick = {
                        showOpenAIKeyDialog.value = true
                    })
                    buildSettingItem("Set instruction", onClick = {
                        showOpenInstructionDialog.value = true
                    })
                    buildSettingItem("How to use", onClick = {

                    })

                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            //lingoNote
            Card(
                shape = CardDefaults.shape,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                ) {
                    Text(
                        text = "LingoNote",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = pretendard,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    buildSettingItem("Support LingoNote", onClick = {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse("market://details?id=" + context.packageName)
                        }
                        context.startActivity(intent)
                    })
                    buildSettingItem("Contact the developer", onClick = {
                        val emailSelectorIntent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:")
                        }
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            putExtra(Intent.EXTRA_EMAIL, arrayOf("devmus81@gmail.com"))
                        }

                        intent.selector = emailSelectorIntent

                        val sendIntent =  if (intent.resolveActivity(context.packageManager) != null) {
                            intent
                        } else {
                            Intent.createChooser(intent, "")
                        }

                        context.startActivity(sendIntent)
                    })
                }
            }
        }
    }
    DialogForAPIKey(
        showDialog = showOpenAIKeyDialog.value,
        onDismiss = { showOpenAIKeyDialog.value = false },
        onConfirm = {
            showOpenAIKeyDialog.value = false
            sharedSettingViewModel.setOpenAIKey(it)
        }
    )
    DialogForInstruction(
        showDialog = showOpenInstructionDialog.value,
        onDismiss = { showOpenInstructionDialog.value = false },
        onConfirm = {
            showOpenInstructionDialog.value = false
            sharedSettingViewModel.setCorrectInstruction(it)
        }
    )
}

@Composable
fun buildSettingItem(title: String, onClick: ()-> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable {
                onClick()
            }
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                fontFamily = pretendard,
                color = MaterialTheme.colorScheme.onPrimary
            )
        )
        Icon(
            modifier = Modifier.size(32.dp),
            painter = painterResource(R.drawable.ic_baseline_keyboard_arrow_right_24),
            contentDescription = "next",
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogForAPIKey(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var keyText by remember {
        mutableStateOf("")
    }

    var openAIKey by remember {
        mutableStateOf(sharedSettingViewModel.getOpenAIKey())
    }

    keyText = openAIKey

    if (showDialog) {
        AlertDialog(
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = { onDismiss() },
            containerColor = MaterialTheme.colorScheme.tertiary,
            textContentColor = MaterialTheme.colorScheme.onTertiary,
            text = {
                OutlinedTextField(
                    value = keyText,
                    onValueChange = {
                        keyText = it
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    maxLines = 3,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = pretendard
                    ),
                    label = {
                        Text(
                            text = "OpenAI Key",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = pretendard
                            )
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Please enter your OpenAI key",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = pretendard
                            )
                        )
                    },
                    singleLine = false,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = MaterialTheme.colorScheme.secondary,
                        containerColor = MaterialTheme.colorScheme.background,
                        focusedLabelColor = MaterialTheme.colorScheme.onSecondary,
                        focusedBorderColor = MaterialTheme.colorScheme.onSecondary
                    )
                )
            },
            confirmButton = {
                Button(onClick = {
                    openAIKey = keyText
                    onConfirm(keyText)
                }) {
                    Text("Register")
                }
            },
            dismissButton = {
                Button(onClick = { onDismiss() }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogForInstruction(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var keyText by remember {
        mutableStateOf("")
    }

    var instruction by remember {
        mutableStateOf(sharedSettingViewModel.getInstruction())
    }

    keyText = instruction

    if (showDialog) {
        AlertDialog(
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = { onDismiss() },
            containerColor = MaterialTheme.colorScheme.tertiary,
            textContentColor = MaterialTheme.colorScheme.onTertiary,
            text = {
                OutlinedTextField(
                    value = keyText,
                    onValueChange = {
                        keyText = it
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    maxLines = 3,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = pretendard
                    ),
                    label = {
                        Text(
                            text = "Instruction",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = pretendard
                            )
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Please enter your instruction",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = pretendard
                            )
                        )
                    },
                    singleLine = false,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = MaterialTheme.colorScheme.secondary,
                        containerColor = MaterialTheme.colorScheme.background,
                        focusedLabelColor = MaterialTheme.colorScheme.onSecondary,
                        focusedBorderColor = MaterialTheme.colorScheme.onSecondary
                    )
                )
            },
            confirmButton = {
                Button(onClick = {
                    instruction = keyText
                    onConfirm(keyText)
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                Button(onClick = { onDismiss() }) {
                    Text("Cancel")
                }
            }
        )
    }
}