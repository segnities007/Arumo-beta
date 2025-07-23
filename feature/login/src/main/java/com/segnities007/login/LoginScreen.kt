package com.segnities007.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.segnities007.ui.CircleIcon
import org.koin.androidx.compose.koinViewModel

private const val PAGE = 3

@Composable
fun LoginScreen(
    navigate: () -> Unit,
    snackBar: (String) -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { PAGE })
    val loginViewModel: LoginViewModel = koinViewModel()

    LaunchedEffect(loginViewModel.effect) {
        loginViewModel.effect.collect {
            when (it) {
                is LoginEffect.Navigation -> navigate()
                is LoginEffect.SnackBar -> snackBar(it.message)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(state = pagerState) { page ->
            when (page) {
                0 -> Start()
                1 -> SignUp()
                2 -> SignIn()
            }
        }
    }
}

@Composable
fun SignUp() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CircleIcon(
            url = "",
            description = "",
        )
    }
}

@Composable
fun SignIn() {
}

@Composable
fun Start() {
}

@Composable
@Preview(showBackground = true)
private fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(
            navigate = {},
            snackBar = {},
        )
    }
}
