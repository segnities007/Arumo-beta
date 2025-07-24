package com.segnities007.storage.component

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.segnities007.model.Storage
import com.segnities007.ui.card.ContiguousImageCard

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
internal fun RowCards(
    modifier: Modifier = Modifier,
    storages: List<Storage>,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(state = rememberScrollState()),
    ) {
        for (storage in storages) {
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                ContiguousImageCard(
                    uri = storage.iconUri,
                    size = 128,
                    onClick = {},
                )
                Text(
                    text = storage.name,
                    textAlign = TextAlign.Center,
                    fontSize = 36.sp,
                )
            }
        }
    }
}
