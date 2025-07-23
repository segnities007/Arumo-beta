package com.segnities007.ui.icon

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.segnities007.ui.R

@Composable
fun CirclePickUpIcon(
    uri: Uri?,
    size: Int = 300,
    pickMedia: ActivityResultLauncher<PickVisualMediaRequest>,
) {
    if (uri != null) {
        AsyncImage(
            modifier =
                Modifier
                    .size(size.dp)
                    .clip(CircleShape)
                    .clickable {
                        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    },
            model = uri,
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
    } else {
        CircleIcon(
            modifier =
                Modifier
                    .clip(CircleShape)
                    .clickable {
                        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    },
            size = size,
            painter = painterResource(R.drawable.touch),
        )
    }
}

@Composable
@Preview(showBackground = true, apiLevel = 35)
private fun CirclePickUpIconPreview() {
    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { newUri -> }

    CirclePickUpIcon(
        uri = null,
        pickMedia = pickMedia,
    )
}
