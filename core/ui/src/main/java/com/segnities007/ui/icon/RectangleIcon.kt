package com.segnities007.ui.icon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.segnities007.ui.R
import com.segnities007.ui.theme.backgroundBrush

@Composable
fun RectangleIcon(
    modifier: Modifier = Modifier,
    height: Int = 200,
    shape: Int = 16,
    painter: Painter,
) {
    Image(
        modifier =
            modifier
                .fillMaxWidth()
                .height(height.dp)
                .clip(RoundedCornerShape(shape.dp)),
        painter = painter,
        contentDescription = "Rectangle Icon",
        contentScale = ContentScale.Crop,
    )
}

@Composable
@Preview(showBackground = true, apiLevel = 35)
private fun RectangleIconPreview() {
    Box(
        modifier = Modifier.background(brush = backgroundBrush),
    ) {
        RectangleIcon(
            painter = painterResource(id = R.drawable.cardboard),
        )
    }
}
