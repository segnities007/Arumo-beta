package com.segnities007.ui.icon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.segnities007.ui.R
import com.segnities007.ui.theme.backgroundBrush

@Composable
fun CircleIcon(
    modifier: Modifier = Modifier,
    painter: Painter,
){
    Image(
        modifier = modifier.clip(CircleShape),
        painter = painter,
        contentDescription = "Circle Icon"
    )
}

@Composable
@Preview(showBackground = true, apiLevel = 35)
private fun CircuitIconPreview(){
    Box(
        modifier = Modifier.background(brush = backgroundBrush)
    ){
        CircleIcon(
            painter = painterResource(id = R.drawable.cardboard)
        )
    }
}