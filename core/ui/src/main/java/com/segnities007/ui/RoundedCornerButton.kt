package com.segnities007.ui

import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun RoundedCornerButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    ElevatedButton(
        onClick = onClick,
    ) {
        Text(
            modifier = modifier,
            text = "Rounded Corner Button",
        )
    }
}

@Composable
@Preview(showBackground = true)
fun RoundedCornerButtonPreview() {
    RoundedCornerButton(
        text = "Rounded Corner Button",
        onClick = {},
    )
}
