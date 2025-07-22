package com.segnities007.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel

private const val PAGE = 3

@Composable
fun LoginScreen() {
    val pagerState = rememberPagerState(pageCount = { PAGE })
    val LoginViewModel: LoginViewModel = koinViewModel()

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
}

@Composable
fun SignIn() {
}

@Composable
fun Start() {
}
