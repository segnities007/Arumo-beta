package com.segnities007.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.segnities007.home.HomeScreen
import com.segnities007.login.LoginScreen
import com.segnities007.model.route.Route
import com.segnities007.storage.StorageScreen
import com.segnities007.ui.theme.backgroundBrush
import org.koin.androidx.compose.koinViewModel


@Composable
fun Navigation() {
    val navController = rememberNavController()
    val navigationViewModel: NavigationViewModel = koinViewModel()
    val state by navigationViewModel.state.collectAsState()

    val setTopBar: (@Composable () -> Unit) -> Unit = {
        navigationViewModel.handleIntent(NavigationIntent.SetTopBar(it))
    }
    val setBottomBar: (@Composable () -> Unit) -> Unit = {
        navigationViewModel.handleIntent(NavigationIntent.SetBottomBar(it))
    }
    val setFab: (@Composable () -> Unit) -> Unit = {
        navigationViewModel.handleIntent(NavigationIntent.SetFab(it))
    }

    LaunchedEffect(Unit) {
        navigationViewModel.handleIntent(NavigationIntent.Init)
    }

    LaunchedEffect(Unit){
        navigationViewModel.effect.collect{ effect ->
            when(effect){
                is NavigationEffect.Navigate -> {
                    navController.navigate(effect.route)
                }
                is NavigationEffect.ShowToast -> {
                    //TODO
                }
            }
        }
    }

    NavUi(
        topBar = state.topBar,
        bottomBar = state.bottomBar,
        fab = state.fab,
    ){
        NavHost(navController = navController, startDestination = Route.Login) {
            composable<Route.Login> {
                LoginScreen(
                    navigate = { navController.navigate(it) },
                    snackBar = { /*TODO*/ },
                )
            }
            composable<Route.Storage> {
                StorageScreen(
                    navigate = { navController.navigate(it) },
                    snackBar = { /*TODO*/ },
                )
            }
            composable<Route.Home> {
                HomeScreen(
                    setTopBar = setTopBar,
                    setBottomBar = setBottomBar,
                    setFab = setFab,
                )
            }
            composable<Route.Search> {
            }
            composable<Route.Dashboard> {
            }
            composable<Route.Setting> {
            }
        }
    }
}

@Composable
private fun NavUi(
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
    fab: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = topBar,
        bottomBar = bottomBar,
        floatingActionButton = fab,
    ){
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(backgroundBrush)
                    .padding(it),
        ) {
            content()
        }
    }
}
