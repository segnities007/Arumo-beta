package com.segnities007.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.koinInject

@Composable
fun HomeScreen(
    setTopBar: (@Composable () -> Unit) -> Unit,
    setBottomBar: (@Composable () -> Unit) -> Unit,
    setFab: (@Composable () -> Unit) -> Unit,
){
    val homeViewModel: HomeViewModel = koinInject()
    val state by homeViewModel.state.collectAsState()
    setTopBar {  }
    setBottomBar {  }
    setFab { }
    HomeUi(state)
}

@Composable
private fun HomeUi(
    state: HomeState,
){

}