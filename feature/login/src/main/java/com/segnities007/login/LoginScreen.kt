package com.segnities007.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.segnities007.login.page.SignIn
import com.segnities007.login.page.SignUp
import com.segnities007.login.page.Start
import com.segnities007.ui.R
import com.segnities007.ui.RoundedCornerButton
import com.segnities007.ui.icon.CircleIcon
import com.segnities007.ui.theme.backgroundBrush
import kotlinx.coroutines.launch
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
                0 -> Start(onSlide = pagerState::animateScrollToPage)
                1 -> SignUp(onSlide = pagerState::animateScrollToPage)
                2 -> SignIn(
                    onSlide = pagerState::animateScrollToPage,
                    onIntent = loginViewModel::handleIntent
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, apiLevel = 35)
private fun LoginScreenPreview() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(brush = backgroundBrush)
    ){
        SignUp {  }
    }
}
