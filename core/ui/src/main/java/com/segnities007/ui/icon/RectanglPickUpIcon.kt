package com.segnities007.ui.icon

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.segnities007.ui.R

@Composable
fun RectanglePickUpIcon(
    uri: Uri?,
    initialIcon: Int = R.drawable.cardboard,
    height: Int = 200,
    shape: Int = 16,
    pickMedia: ActivityResultLauncher<PickVisualMediaRequest>,
) {
    if (uri != null) {
        AsyncImage(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(height.dp)
                    .clip(RoundedCornerShape(shape.dp))
                    .clickable {
                        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    },
            model = uri,
            contentScale = ContentScale.FillWidth,
            contentDescription = null,
        )
    } else {
        RectangleIcon(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(height.dp)
                    .clip(RoundedCornerShape(shape.dp))
                    .clickable {
                        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    },
            painter = painterResource(initialIcon),
        )
    }
}

@Composable
@Preview(showBackground = true, apiLevel = 35)
private fun RectanglePickUpIconPreview() {
    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { newUri -> }

    RectanglePickUpIcon(
        uri = null,
        pickMedia = pickMedia,
    )
}
