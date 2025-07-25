package com.segnities007.storage.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.segnities007.storage.StorageIntent
import com.segnities007.ui.RoundedCornerButton
import com.segnities007.ui.icon.RectanglePickUpIcon
import com.segnities007.ui.input.OutlineTextField
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
internal fun BottomSheet(
    showBottomSheet: Boolean,
    onIntent: (StorageIntent) -> Unit,
    onUpdateShowBottomSheet: (Boolean) -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var uri by remember { mutableStateOf<Uri?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    val onCloseBottomSheet = {
        scope.launch { sheetState.hide() }.invokeOnCompletion {
            if (!sheetState.isVisible) {
                onUpdateShowBottomSheet(false)
            }
        }
    }
    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { newUri ->
            uri = newUri
        }

    if (showBottomSheet) {
        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.surface,
            onDismissRequest = {
                onUpdateShowBottomSheet(false)
            },
            sheetState = sheetState,
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 48.dp, start = 48.dp, end = 48.dp),
                verticalArrangement = Arrangement.spacedBy(36.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Create Storage",
                    textAlign = TextAlign.Center,
                    fontSize = 36.sp,
                    maxLines = 1,
                )
                RectanglePickUpIcon(
                    uri = uri,
                    initialIcon = com.segnities007.ui.R.drawable.touch,
                    pickMedia = pickMedia,
                )
                Column {
                    OutlineTextField(
                        label = "Name",
                        value = name,
                        onValueChange = { name = it },
                    )
                    OutlineTextField(
                        label = "Description",
                        value = description,
                        onValueChange = { description = it },
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                ) {
                    RoundedCornerButton(
                        modifier = Modifier.weight(1f),
                        text = "Close",
                        onClick = { onCloseBottomSheet() },
                    )
                    RoundedCornerButton(
                        modifier = Modifier.weight(1f),
                        text = "Create",
                        onClick = {
                            onIntent(
                                StorageIntent.CreateStorage(
                                    name = name,
                                    description = description,
                                    iconUri = uri,
                                ),
                            )
                            onCloseBottomSheet()
                                  },
                    )
                }
            }
        }
    }
}
