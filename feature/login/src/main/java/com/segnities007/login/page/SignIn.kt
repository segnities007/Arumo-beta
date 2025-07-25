package com.segnities007.login.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.segnities007.login.LoginIntent
import com.segnities007.ui.R
import com.segnities007.ui.RoundedCornerButton
import com.segnities007.ui.icon.CircleIcon
import kotlinx.coroutines.launch

@Composable
internal fun SignIn(
    onSlide: suspend (Int) -> Unit,
    onIntent: (LoginIntent) -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Box {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            Spacer(modifier = Modifier.weight(0.5f))
            CircleIcon(
                painter = painterResource(R.drawable.cardboard),
            )
            Spacer(modifier = Modifier.height(64.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email") },
                colors =
                    OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                    ),
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password") },
                colors =
                    OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                    ),
            )
            Spacer(modifier = Modifier.height(64.dp))
            RoundedCornerButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Sign In",
                onClick = {
                    onIntent(
                        LoginIntent.SignIn(
                            email = email,
                            password = password,
                        ),
                    )
                },
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        Text(
            modifier =
                Modifier
                    .padding(bottom = 32.dp)
                    .clickable {
                        coroutineScope.launch {
                            onSlide(0)
                        }
                    }.align(Alignment.BottomCenter),
            text = "Back to Start",
            fontSize = 16.sp,
        )
    }
}

@Composable
@Preview(showBackground = true, apiLevel = 35)
private fun SignInPreview() {
    SignIn(
        onSlide = {},
        onIntent = {},
    )
}
