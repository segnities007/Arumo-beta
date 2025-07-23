package com.segnities007.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.segnities007.login.LoginScreen
import com.segnities007.model.route.Route
import com.segnities007.ui.theme.backgroundBrush

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavUi {
        NavHost(navController = navController, startDestination = Route.Login) {
            composable<Route.Login> {
                LoginScreen(
                    navigate = { navController.navigate(it) },
                    snackBar = { /*TODO*/ },
                )
            }
            composable<Route.Storage> {
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
