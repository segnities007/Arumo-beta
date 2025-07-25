package com.segnities007.navigation

import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
    val effect by navigationViewModel.effect.collectAsState(initial = null)

    LaunchedEffect(Unit) {
        navigationViewModel.handleIntent(NavigationIntent.Init)
    }

    LaunchedEffect(effect){
        when(effect){
            is NavigationEffect.Navigate -> {
                navController.navigate((effect as NavigationEffect.Navigate).route)
            }
            is NavigationEffect.ShowToast -> {
                //TODO
            }
            null -> { /*Nothing*/ }
        }
    }

    NavUi {
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
private fun NavUi(content: @Composable () -> Unit) {
    Scaffold {
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
