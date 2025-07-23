package com.segnities007.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.Login){
        composable<Route.Login>{

        }
        composable<Route.Home>{

        }
        composable<Route.Search>{

        }
        composable<Route.Dashboard>{

        }
        composable<Route.Setting>{

        }
        composable<Route.Storage>{

        }
    }
}