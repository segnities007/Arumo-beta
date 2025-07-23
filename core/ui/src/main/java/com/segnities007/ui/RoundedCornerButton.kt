package com.segnities007.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RoundedCornerButton(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: Int = 24,
    onClick: () -> Unit,
) {
    ElevatedButton(
        modifier = modifier,
        onClick = onClick,
        contentPadding =
            PaddingValues(
                horizontal = 24.dp,
                vertical = 16.dp,
            ),
    ) {
        Text(
            text = text,
            maxLines = 1,
            fontSize = fontSize.sp,
        )
    }
}

@Composable
@Preview(showBackground = true, apiLevel = 35)
private fun RoundedCornerButtonPreview() {
    RoundedCornerButton(
        text = "Rounded Corner Button",
        onClick = {},
    )
}
