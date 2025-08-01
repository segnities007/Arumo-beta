package com.segnities007.arumo_beta.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

private val lightBlue = Color.Yellow.copy(alpha = 0.2f) // 青を50%の透明度に
private val lightGreen = Color.Green.copy(alpha = 0.3f) // 緑を50%の透明度に

val backgroundBrush =
    Brush.linearGradient(
        colors = listOf(lightBlue, lightGreen),
        start = Offset(0f, 0f),
    )
