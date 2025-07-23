package com.segnities007.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import coil3.compose.AsyncImage

@Composable
fun CircleIcon(
    modifier: Modifier = Modifier,
    url: String,
    description: String,
    onClick: () -> Unit = {},
) {
    Box(
        modifier =
            modifier
                .clip(CircleShape)
                .clickable { onClick() },
    ) {
        AsyncImage(
            model = url,
            contentDescription = description,
        )
    }
}
