package com.segnities007.ui.card

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.segnities007.ui.R

@Composable
fun ContiguousImageCard(
    modifier: Modifier = Modifier,
    uri: Uri? = null,
    enabled: Boolean = true,
    shape: Shape = CardDefaults.elevatedShape,
    colors: CardColors = CardDefaults.elevatedCardColors(),
    size: Int = 100,
    elevation: Int = 2,
    onClick: () -> Unit,
) {
    ElevatedCard(
        modifier =
            modifier
                .size(size.dp),
        elevation =
            CardDefaults.elevatedCardElevation(
                defaultElevation = elevation.dp,
            ),
        shape = shape,
        enabled = enabled,
        colors = colors,
        onClick = onClick,
    ) {
        if (uri != null) {
            AsyncImage(
                modifier =
                    Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                model = uri,
                contentDescription = null,
            )
        } else {
            Image(
                modifier =
                    Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.cardboard),
                contentDescription = null,
            )
        }
    }
}

@Composable
@Preview(showBackground = true, apiLevel = 35)
private fun ContiguousImageCardPreview() {
    ContiguousImageCard(
        onClick = {},
    )
}
