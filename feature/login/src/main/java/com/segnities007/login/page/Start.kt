package com.segnities007.login.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.segnities007.ui.R
import com.segnities007.ui.RoundedCornerButton
import com.segnities007.ui.icon.CircleIcon
import kotlinx.coroutines.launch

@Composable
fun Start(onSlide: suspend (Int) -> Unit) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        CircleIcon(
            painter = painterResource(R.drawable.cardboard),
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Arumo",
            fontSize = 48.sp,
        )
        Spacer(modifier = Modifier.height(64.dp))
        RoundedCornerButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Sign Up",
            onClick = {
                coroutineScope.launch {
                    onSlide(1)
                }
            },
        )
        Spacer(modifier = Modifier.height(32.dp))
        RoundedCornerButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Sign In",
            onClick = {
                coroutineScope.launch {
                    onSlide(2)
                }
            },
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
@Preview(showBackground = true, apiLevel = 35)
private fun StartPreview() {
    Start { }
}
